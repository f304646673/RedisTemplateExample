package org.example.redistemplateexample.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ListOperationTest {

    @Autowired
    private ListOperation listOperation;

    @Test
    public void testBLPop() {

        Pair<String, String> pair = KeyGenerator.generateString("testBLPop");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        List<String> data = listOperation.BLPop(key, 1);
        assertNotNull(data);
        assertEquals(0, data.size());

        listOperation.LPush(key, value);
        data = listOperation.BLPop(key, 1);
        assertNotNull(data);
        assertEquals(1, data.size());
        assertEquals(value, data.get(0));

    }

    @Test
    public void testBRPop() {

        Pair<String, String> pair = KeyGenerator.generateString("testBRPop");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        List<String> data = listOperation.BRPop(key, 1);
        assertNotNull(data);
        assertEquals(0, data.size());

        listOperation.RPush(key, value);
        data = listOperation.BRPop(key, 1);
        assertNotNull(data);
        assertEquals(1, data.size());
        assertEquals(value, data.getFirst());

    }

    @Test
    public void testBRPopLPush() {

        Pair<String, String> pair = KeyGenerator.generateString("testBRPopLPush");
        final String sourceKey = pair.getFirst();
        final String destinationKey = pair.getSecond();
        final String value = "value";

        assertNull(listOperation.BRPopLPush(sourceKey, destinationKey));

        listOperation.RPush(sourceKey, value);
        String data = listOperation.BRPopLPush(sourceKey, destinationKey);
        assertNotNull(data);
        assertEquals(value, data);

        assertEquals(1, listOperation.LLen(destinationKey));

    }

    @Test
    public void testLIndex() {

        Pair<String, String> pair = KeyGenerator.generateString("testLIndex");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertNull(listOperation.LIndex(key, 0));

        listOperation.LPush(key, value);
        String data = listOperation.LIndex(key, 0);
        assertNotNull(data);
        assertEquals(value, data);

    }

    @Test
    public void testLInsert() {

        Pair<String, String> pair = KeyGenerator.generateString("testLInsert");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertEquals(0, listOperation.LInsert(key, "pivot", value, true));

        listOperation.LPush(key, "pivot");
        assertEquals(2, listOperation.LInsert(key, "pivot", value, true));

    }

    @Test
    public void testLLen() {

        Pair<String, String> pair = KeyGenerator.generateString("testLLen");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertEquals(0, listOperation.LLen(key));

        listOperation.LPush(key, value);
        assertEquals(1, listOperation.LLen(key));

    }

    @Test
    public void testLPop() {

        Pair<String, String> pair = KeyGenerator.generateString("testLPop");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertNull(listOperation.LPop(key));

        listOperation.LPush(key, value);
        String data = listOperation.LPop(key);
        assertNotNull(data);
        assertEquals(value, data);

    }

    @Test
    public void testLPush() {

        Pair<String, String> pair = KeyGenerator.generateString("testLPush");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertEquals(1, listOperation.LPush(key, value));
        assertEquals(2, listOperation.LPush(key, value));

    }

    @Test
    public void testLPushX() {

        Pair<String, String> pair = KeyGenerator.generateString("testLPushX");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertEquals(0, listOperation.LPushX(key, value));

        listOperation.LPush(key, value);
        assertEquals(2, listOperation.LPushX(key, value));

    }

    @Test
    public void testLRange() {

        Pair<String, String> pair = KeyGenerator.generateString("testLRange");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertTrue(listOperation.LPush(key, value) > 0);
        assertTrue(listOperation.LPush(key, value) > 0);

        List<String> data = listOperation.LRange(key, 0, -1);
        assertNotNull(data);
        assertEquals(2, data.size());
        assertEquals(value, data.get(0));
        assertEquals(value, data.get(1));

    }

    @Test
    public void testLRem() {

        Pair<String, String> pair = KeyGenerator.generateString("testLRem");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertEquals(0, listOperation.LRem(key, 0, value));

        listOperation.LPush(key, value);
        assertEquals(1, listOperation.LRem(key, 0, value));

    }

    @Test
    public void testLSet() {

        Pair<String, String> pair = KeyGenerator.generateString("testLSet");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        listOperation.LPush(key, value);
        listOperation.LSet(key, 0, "newValue");

        String data = listOperation.LIndex(key, 0);
        assertNotNull(data);
        assertEquals("newValue", data);

    }

    @Test
    public void testLTrim() {

        Pair<String, String> pair = KeyGenerator.generateString("testLTrim");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        listOperation.LPush(key, value);
        listOperation.LPush(key, value);

        listOperation.LTrim(key, 0, 0);

        List<String> data = listOperation.LRange(key, 0, -1);
        assertNotNull(data);
        assertEquals(1, data.size());
        assertEquals(value, data.getFirst());

    }

    @Test
    public void testRPop() {

        Pair<String, String> pair = KeyGenerator.generateString("testRPop");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertNull(listOperation.RPop(key));

        listOperation.RPush(key, value);
        String data = listOperation.RPop(key);
        assertNotNull(data);
        assertEquals(value, data);

    }

    @Test
    public void testRPopLPush() {

        Pair<String, String> pair = KeyGenerator.generateString("testRPopLPush");
        final String sourceKey = pair.getFirst();
        final String destinationKey = pair.getSecond();
        final String value = "value";

        assertNull(listOperation.RPopLPush(sourceKey, destinationKey));

        listOperation.RPush(sourceKey, value);
        String data = listOperation.RPopLPush(sourceKey, destinationKey);
        assertNotNull(data);
        assertEquals(value, data);

    }

    @Test
    public void testRPush() {

        Pair<String, String> pair = KeyGenerator.generateString("testRPush");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertEquals(1, listOperation.RPush(key, value));
        assertEquals(2, listOperation.RPush(key, value));

    }

    @Test
    public void testRPushX() {

        Pair<String, String> pair = KeyGenerator.generateString("testRPushX");
        final String key = pair.getFirst();
        final String value = pair.getSecond();

        assertEquals(0, listOperation.RPushX(key, value));

        listOperation.RPush(key, value);
        assertEquals(2, listOperation.RPushX(key, value));

    }

}
