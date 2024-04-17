package org.example.redistemplateexample.redis;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StringOperationTest {

    @Autowired
    private StringOperation stringOperation;

    @Test
    public void testSet() {
        Pair<String, String> pair = KeyGenerator.generate("testSet");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertDoesNotThrow(() -> {
            assertTrue(stringOperation.Set(key, value));
        });
    }

    @Test
    public void testGet() {
        Pair<String, String> pair = KeyGenerator.generate("testGet");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertDoesNotThrow(() -> {
            assertNull(stringOperation.Get(key));

            assertTrue(stringOperation.Set(key, value));
            String data = stringOperation.Get(key);
            assertNotNull(data);
            assertEquals(value, data);
        });
    }
}
