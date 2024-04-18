package org.example.redistemplateexample.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LongOperationTest {
    
        @Autowired
        private LongOperation longOperation;
    
        @Test
        public void testSet() {
            Pair<String, Long> pair = KeyGenerator.generateLong("testSet");
            final String key = pair.getFirst();
            final Long value = pair.getSecond();
    
            assertDoesNotThrow(() -> {
                longOperation.Set(key, value);
            });
        }
    
        @Test
        public void testGet() {
            Pair<String, Long> pair = KeyGenerator.generateLong("testGet");
            final String key = pair.getFirst();
            final Long value = pair.getSecond();
    
            assertDoesNotThrow(() -> {
                assertNull(longOperation.Get(key));
    
                longOperation.Set(key, value);
                Long data = longOperation.Get(key);
                assertNotNull(data);
                assertEquals(value, data);
            });
        }
    
        @Test
        public void testGetAndSet() {
            Pair<String, Long> pair = KeyGenerator.generateLong("testGetAndSet");
            final String key = pair.getFirst();
            final Long value = pair.getSecond();
    
            assertDoesNotThrow(() -> {
                assertNull(longOperation.Get(key));
    
                Long data = longOperation.GetAndSet(key, value);
                assertNull(data);
    
                data = longOperation.Get(key);
                assertNotNull(data);
                assertEquals(value, data);
            });
        }
    
        @Test
        public void testIncrement() {
            Pair<String, Long> pair = KeyGenerator.generateLong("testIncrement");
            final String key = pair.getFirst();
    
            assertDoesNotThrow(() -> {
                assertNull(longOperation.Get(key));
    
                Long data = longOperation.Increment(key);
                assertNotNull(data);
                assertEquals(1, data);
    
                data = longOperation.Increment(key);
                assertNotNull(data);
                assertEquals(2, data);
            });
        }
    
        @Test
        public void testIncrementBy() {
            Pair<String, Long> pair = KeyGenerator.generateLong("testIncrementBy");
            final String key = pair.getFirst();
            final Long value = pair.getSecond();
    
            assertDoesNotThrow(() -> {
                assertNull(longOperation.Get(key));
    
                Long data = longOperation.IncrementBy(key, value);
                assertNotNull(data);
                assertEquals(value, data);
    
                data = longOperation.IncrementBy(key, value);
                assertNotNull(data);
                assertEquals(value * 2, data);
            });
        }
    
        @Test
        public void testDecrement() {
            Pair<String, Long> pair = KeyGenerator.generateLong("testDecrement");
            final String key = pair.getFirst();

            assertDoesNotThrow(() -> {
                assertNull(longOperation.Get(key));
    
                Long data = longOperation.Decrement(key);
                assertNotNull(data);
                assertEquals(-1, data);
    
                data = longOperation.Decrement(key);
                assertNotNull(data);
                assertEquals(-2, data);
            });
        }

        @Test
        public void testDecrementBy() {
            Pair<String, Long> pair = KeyGenerator.generateLong("testDecrementBy");
            final String key = pair.getFirst();
            final Long value = pair.getSecond();
    
            assertDoesNotThrow(() -> {
                assertNull(longOperation.Get(key));
    
                Long data = longOperation.DecrementBy(key, value);
                assertNotNull(data);
                assertEquals(-value, data);
    
                data = longOperation.DecrementBy(key, value);
                assertNotNull(data);
                assertEquals(-value * 2, data);
            });
        }
}
