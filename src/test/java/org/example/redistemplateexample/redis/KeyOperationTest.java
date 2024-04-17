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
            Pair<String, String> pair = KeyGenerator.generate("testDel-Single");
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
                Pair<String, String> pair = KeyGenerator.generate("testDel-Multiple");
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
        Pair<String, String> pair = KeyGenerator.generate("testDel");
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
        Pair<String, String> pair = KeyGenerator.generate("testExpire");
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

}
