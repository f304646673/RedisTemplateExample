package org.example.redistemplateexample.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.util.Pair;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;


@SpringBootTest
public class KeyOperationTest {

    @Autowired
    private KeyOperation keyOperation;

    @Autowired
    private StringOperation stringOperation;


    @Test
    public void testDel() {
        assertDoesNotThrow(() -> {
            Pair<String, String> pair = KeyGenerator.generateString("testDel-Single");
            final String key = pair.getFirst();
            final String value = pair.getSecond();

            assertFalse(keyOperation.Exists(value));

            stringOperation.Set(key, value);
            assertTrue(keyOperation.Exists(key));

            assertTrue(keyOperation.Del(key));
            assertFalse(keyOperation.Exists(value));
        });

        assertDoesNotThrow(() -> {
            Collection<String> allKeys = new ArrayList<>();
            Collection<String> halfKeys = new ArrayList<>();
            int fullsize = 10;
            int halfsize = fullsize / 2;
            for (int i = 0; i < fullsize; i++) {
                Pair<String, String> pair = KeyGenerator.generateString("testDel-Multiple"+i);
                final String key = pair.getFirst();
                final String value = pair.getSecond();
                allKeys.add(key);
                if (i < halfsize) {
                    halfKeys.add(key);
                }

                stringOperation.Set(key, value);
            }

            assertEquals(halfsize, keyOperation.Del(halfKeys));
            assertEquals(fullsize - halfsize, keyOperation.Del(allKeys));
        });
    }

    @Test
    public void testExists() {
        Pair<String, String> pair = KeyGenerator.generateString("testDel");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertDoesNotThrow(() -> {
            assertFalse(keyOperation.Exists(key));
            stringOperation.Set(key, value);
            assertTrue(keyOperation.Exists(key));
        });
    }

    @Test
    public void testExpire() {
        Pair<String, String> pair = KeyGenerator.generateString("testExpire");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertDoesNotThrow(() -> {
            stringOperation.Set(key, value);
            assertTrue(keyOperation.Expire(key, 1, TimeUnit.SECONDS));
            assertTrue(keyOperation.Exists(key));
            Thread.sleep(1000);
            assertFalse(keyOperation.Exists(key));
        });
    }

    @Test
    public void testExpireAt() {
        Pair<String, String> pair = KeyGenerator.generateString("testExpireAt");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertDoesNotThrow(() -> {
            stringOperation.Set(key, value);
            assertTrue(keyOperation.ExpireAt(key, new java.util.Date(System.currentTimeMillis() + 1000)));
            assertTrue(keyOperation.Exists(key));
            Thread.sleep(1000);
            assertFalse(keyOperation.Exists(key));
        });
    }

    @Test
    public void testPersist() {
        Pair<String, String> pair = KeyGenerator.generateString("testPersist");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertDoesNotThrow(() -> {
            stringOperation.Set(key, value);
            assertTrue(keyOperation.Expire(key, 1, TimeUnit.SECONDS));
            assertTrue(keyOperation.Exists(key));
            assertTrue(keyOperation.Persist(key));
            assertTrue(keyOperation.Exists(key));
        });
    }

    @Test
    public void testTTL() {
        Pair<String, String> pair = KeyGenerator.generateString("testTTL");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertDoesNotThrow(() -> {
            stringOperation.Set(key, value);
            assertTrue(keyOperation.Expire(key, 1, TimeUnit.SECONDS));
            assertTrue(keyOperation.Exists(key));
            assertTrue(keyOperation.TTL(key) > 0);
            Thread.sleep(1000);
            assertEquals(-2, keyOperation.TTL(key));
        });
    }

    @Test
    public void testType() {
        Pair<String, String> pair = KeyGenerator.generateString("testType");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertDoesNotThrow(() -> {
            stringOperation.Set(key, value);
            assertEquals("string", keyOperation.Type(key));
        });
    }

//    @Test
//    public void testKeys() {
//        assertDoesNotThrow(() -> {
//            Collection<String> allKeys = new ArrayList<>();
//            int fullsize = 10;
//            for (int i = 0; i < fullsize; i++) {
//                Pair<String, String> pair = KeyGenerator.generateString("testKeys" + i);
//                final String key = pair.getFirst();
//                final String value = pair.getSecond();
//                allKeys.add(key);
//
//                stringOperation.Set(key, value);
//            }
//
//            Collection<String> keys = keyOperation.Keys("test*");
//            assertEquals(allKeys.size(), keys.size());
//            assertTrue(allKeys.containsAll(keys));
//        });
//    }

    @Test
    public void testRename() {
        Pair<String, String> pair = KeyGenerator.generateString("testRename");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertDoesNotThrow(() -> {
            Pair<String, String> pair2 = KeyGenerator.generateString("testRename2");
            final String newKey = pair2.getFirst();
            final String newValue = pair2.getSecond();

            stringOperation.Set(key, value);
            assertTrue(keyOperation.Rename(key, newKey));
            assertFalse(keyOperation.Exists(key));
            assertTrue(keyOperation.Exists(newKey));
        });
    }

    @Test
    public void testRenameNX() {
        Pair<String, String> pair = KeyGenerator.generateString("testRenameNX");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertDoesNotThrow(() -> {
            Pair<String, String> pair2 = KeyGenerator.generateString("testRenameNX2");
            final String newKey = pair2.getFirst();
            final String newValue = pair2.getSecond();

            stringOperation.Set(key, value);

            assertTrue(keyOperation.RenameNX(key, newKey));
            assertFalse(keyOperation.Exists(key));
            assertTrue(keyOperation.Exists(newKey));
        });
    }

    // @Test
    // public void testSort() {
    //     Pair<String, String> pair = KeyGenerator.generate("testSort");
    //     final String key = pair.getFirst();
    //     final String value = pair.getSecond();

    //     assertDoesNotThrow(() -> {
    //         Pair<String, String> pair2 = KeyGenerator.generate("testSort2");
    //         final String destKey = pair2.getFirst();
    //         final String destValue = pair2.getSecond();

    //         stringOperation.Set(key, value);
    //         stringOperation.Set(destKey, destValue);
    //         // assertTrue(keyOperation.Sort(key, destKey));
    //     });
    // }
}
