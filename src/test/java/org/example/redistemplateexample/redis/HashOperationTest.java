package org.example.redistemplateexample.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HashOperationTest {

    @Autowired
    private HashOperation hashOperation;

    @Test
    public void testHSet() {
        Pair<String, ArrayList<Pair<String, String>>> pair = KeyGenerator.generateHashString("testHSet", 1);
        final String key = pair.getFirst();
        final String field = pair.getSecond().getFirst().getFirst();
        final String value = pair.getSecond().getFirst().getSecond();


        assertDoesNotThrow(() -> {
            hashOperation.HSet(key, field, value);
        });
    }

    @Test
    public void testHGet() {
        Pair<String, ArrayList<Pair<String, String>>> pair = KeyGenerator.generateHashString("testHGet", 1);
        final String key = pair.getFirst();
        final String field = pair.getSecond().getFirst().getFirst();
        final String value = pair.getSecond().getFirst().getSecond();

        assertDoesNotThrow(() -> {
            assertNull(hashOperation.HGet(key, field));

            hashOperation.HSet(key, field, value);
            String data = hashOperation.HGet(key, field);
            assertNotNull(data);
            assertEquals(value, data);
        });
    }
    
    @Test
    public void testHGetAll() {
        Pair<String, ArrayList<Pair<String, String>>> pair = KeyGenerator.generateHashString("testHGetAll", 1);
        final String key = pair.getFirst();
        final String field = pair.getSecond().getFirst().getFirst();
        final String value = pair.getSecond().getFirst().getSecond();

        assertDoesNotThrow(() -> {
            assertTrue(hashOperation.HGetAll(key).isEmpty());

            hashOperation.HSet(key, field, value);
            assertEquals(1, hashOperation.HGetAll(key).size());
        });
    }

    @Test
    public void testHDel() {
        Pair<String, ArrayList<Pair<String, String>>> pair = KeyGenerator.generateHashString("testHDel", 1);
        final String key = pair.getFirst();
        final String field = pair.getSecond().getFirst().getFirst();
        final String value = pair.getSecond().getFirst().getSecond();

        assertDoesNotThrow(() -> {
            assertFalse(hashOperation.HExists(key, field));

            hashOperation.HSet(key, field, value);
            assertTrue(hashOperation.HExists(key, field));
            assertEquals(1, hashOperation.HDel(key, field));
            
            assertFalse(hashOperation.HExists(key, field));
        });
    }

    @Test
    public void testHExists() {
        Pair<String, ArrayList<Pair<String, String>>> pair = KeyGenerator.generateHashString("testHExists", 1);
        final String key = pair.getFirst();
        final String field = pair.getSecond().getFirst().getFirst();
        final String value = pair.getSecond().getFirst().getSecond();


        assertDoesNotThrow(() -> {
            assertFalse(hashOperation.HExists(key, field));

            hashOperation.HSet(key, field, value);
            assertTrue(hashOperation.HExists(key, field));
        });
    }

    @Test
    public void testHIncrBy() {
        Pair<String, ArrayList<Pair<String, Long>>> pair = KeyGenerator.generateHashLong("testHIncrBy", 1);
        final String key = pair.getFirst();
        final String field = pair.getSecond().getFirst().getFirst();
        final Long value = pair.getSecond().getFirst().getSecond();


        assertDoesNotThrow(() -> {
            assertEquals(value, hashOperation.HIncrBy(key, field, value));
            assertEquals(value * 2, hashOperation.HIncrBy(key, field, value));
        });
    }

    @Test
    public void testHKeys() {
        Pair<String, ArrayList<Pair<String, String>>> pair = KeyGenerator.generateHashString("testHKeys", 1);
        final String key = pair.getFirst();
        final String field = pair.getSecond().getFirst().getFirst();
        final String value = pair.getSecond().getFirst().getSecond();

        assertDoesNotThrow(() -> {
            assertTrue(hashOperation.HKeys(key).isEmpty());

            hashOperation.HSet(key, field, value);
            assertEquals(1, hashOperation.HKeys(key).size());
        });
    }

    @Test
    public void testHLen() {
        Pair<String, ArrayList<Pair<String, String>>> pair = KeyGenerator.generateHashString("testHLen", 1);
        final String key = pair.getFirst();
        final String field = pair.getSecond().getFirst().getFirst();
        final String value = pair.getSecond().getFirst().getSecond();

        assertDoesNotThrow(() -> {
            assertEquals(0, hashOperation.HLen(key));

            hashOperation.HSet(key, field, value);
            assertEquals(1, hashOperation.HLen(key));
        });
    }

    @Test
    public void testHMGet() {
        Pair<String, ArrayList<Pair<String, String>>> pair = KeyGenerator.generateHashString("testHMGet", 2);
        final String key = pair.getFirst();
        final String field1 = pair.getSecond().get(0).getFirst();
        final String value1 = pair.getSecond().get(0).getSecond();
        final String field2 = pair.getSecond().get(1).getFirst();
        final String value2 = pair.getSecond().get(1).getSecond();


        assertDoesNotThrow(() -> {
            List<Object> fields = new ArrayList<>() {
                {
                    add(field1);
                    add(field2);
                }
            };

            hashOperation.HSet(key, field1, value1);
            hashOperation.HSet(key, field2, value2);
            assertEquals(2, hashOperation.HMGet(key, fields).size());
        });
    }

//    @Test
//    public void testHMSet() {
//        Pair<String, String> pair = KeyGenerator.generateString("testHMSet");
//        final String key = pair.getFirst();
//        final String field1 = pair.getSecond();
//        final String field2 = "field2";
//        final String value1 = "value1";
//        final String value2 = "value2";
//
//        assertDoesNotThrow(() -> {
//            hashOperation.HMSet(key, Pair.of(field1, value1), Pair.of(field2, value2));
//            assertEquals(2, hashOperation.HLen(key));
//        });
//    }


//    @Test
//    public void testHSetNX() {
//        Pair<String, String> pair = KeyGenerator.generateString("testHSetNX");
//        final String key = pair.getFirst();
//        final String field = pair.getSecond();
//        final String value = "value";
//
//        assertDoesNotThrow(() -> {
//            assertTrue(hashOperation.HSetNX(key, field, value));
//            assertFalse(hashOperation.HSetNX(key, field, value));
//        });
//    }
//
//
//    @Test
//    public void testHVals() {
//        Pair<String, String> pair = KeyGenerator.generateString("testHVals");
//        final String key = pair.getFirst();
//        final String field = pair.getSecond();
//        final String value = "value";
//
//        assertDoesNotThrow(() -> {
//            assertTrue(hashOperation.HVals(key).isEmpty());
//
//            hashOperation.HSet(key, field, value);
//            assertEquals(1, hashOperation.HVals(key).size());
//        });
//    }

    

}
