package org.example.redistemplateexample.redis;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.util.Pair;

@SpringBootTest
public class SetOperationTest {

    @Autowired
    private SetOperation setOperation;

    @Test
    public void testSAdd() {
        Pair<String, ArrayList<String>> pair = KeyGenerator.generateSetString("testSAdd", 3);
        final String key = pair.getFirst();
        final ArrayList<String> values = pair.getSecond();

        assertDoesNotThrow(() -> {
            setOperation.SAdd(key, values.toArray(new String[0]));
        });
    }

    @Test
    public void testSCard() {
        Pair<String, ArrayList<String>> pair = KeyGenerator.generateSetString("testSCard", 3);
        final String key = pair.getFirst();
        final ArrayList<String> values = pair.getSecond();

        assertDoesNotThrow(() -> {
            setOperation.SAdd(key, values.toArray(new String[0]));
            assertEquals(values.size(), setOperation.SCard(key));
        });
    }

    @Test
    public void testSDiff() {
        Pair<String, ArrayList<String>> pair = KeyGenerator.generateSetString("testSDiff1", 3);
        final String key = pair.getFirst();
        final ArrayList<String> values = pair.getSecond();

        Pair<String, ArrayList<String>> pairOther = KeyGenerator.generateSetString("testSDiff2", 4);
        final String keyOther = pairOther.getFirst();
        final ArrayList<String> valuesOther = pairOther.getSecond();

        assertDoesNotThrow(() -> {
            setOperation.SAdd(key, values.toArray(new String[0]));
            setOperation.SAdd(keyOther, valuesOther.toArray(new String[0]));

            assertEquals(values.size(), setOperation.SDiff(key, keyOther).size());
        });
    }   

    @Test
    public void testSDiffStore() {
        Pair<String, ArrayList<String>> pair = KeyGenerator.generateSetString("testSDiffStore1", 3);
        final String key = pair.getFirst();
        final ArrayList<String> values = pair.getSecond();

        Pair<String, ArrayList<String>> pairOther = KeyGenerator.generateSetString("testSDiffStore2", 4);
        final String otherKey = pairOther.getFirst();
        final ArrayList<String> valuesOther = pairOther.getSecond();

        Pair<String, ArrayList<String>> pairDest = KeyGenerator.generateSetString("testSDiffStore3", 0);
        final String destKey = pairDest.getFirst();

        assertDoesNotThrow(() -> {
            setOperation.SAdd(key, values.toArray(new String[0]));
            setOperation.SAdd(otherKey, valuesOther.toArray(new String[0]));

            assertEquals(values.size(), setOperation.SDiffStore(key, otherKey, destKey));
            assertEquals(values.size(), setOperation.SMembers(destKey).size());
        });
    }

    @Test
    public void testSInter() {
        Pair<String, ArrayList<String>> pair = KeyGenerator.generateSetString("testSInter1", 3);
        final String key = pair.getFirst();
        final ArrayList<String> values = pair.getSecond();

        Pair<String, ArrayList<String>> pairOther = KeyGenerator.generateSetString("testSInter2", 4);
        final String keyOther = pairOther.getFirst();
        final ArrayList<String> valuesOther = pairOther.getSecond();

        assertDoesNotThrow(() -> {
            setOperation.SAdd(key, values.toArray(new String[0]));
            setOperation.SAdd(keyOther, valuesOther.toArray(new String[0]));

            assertEquals(0, setOperation.SInter(key, keyOther).size());
        });
    }

    @Test
    public void testSInterStore() {
        Pair<String, ArrayList<String>> pair = KeyGenerator.generateSetString("testSInterStore1", 3);
        final String key = pair.getFirst();
        final ArrayList<String> values = pair.getSecond();

        Pair<String, ArrayList<String>> pairOther = KeyGenerator.generateSetString("testSInterStore2", 4);
        final String otherKey = pairOther.getFirst();
        final ArrayList<String> valuesOther = pairOther.getSecond();

        Pair<String, ArrayList<String>> pairDest = KeyGenerator.generateSetString("testSInterStore3", 0);
        final String destKey = pairDest.getFirst();

        assertDoesNotThrow(() -> {
            setOperation.SAdd(key, values.toArray(new String[0]));
            setOperation.SAdd(otherKey, valuesOther.toArray(new String[0]));

            assertEquals(0, setOperation.SInterStore(key, otherKey, destKey));
            assertEquals(0, setOperation.SMembers(destKey).size());
        });
    }

    @Test
    public void testSIsMember() {
        Pair<String, ArrayList<String>> pair = KeyGenerator.generateSetString("testSIsMember", 3);
        final String key = pair.getFirst();
        final ArrayList<String> values = pair.getSecond();

        assertDoesNotThrow(() -> {
            setOperation.SAdd(key, values.toArray(new String[0]));

            for (String value : values) {
                assertEquals(true, setOperation.SIsMember(key, value));
            }
        });
    }

    @Test
    public void testSMembers() {
        Pair<String, ArrayList<String>> pair = KeyGenerator.generateSetString("testSMembers", 3);
        final String key = pair.getFirst();
        final ArrayList<String> values = pair.getSecond();

        assertDoesNotThrow(() -> {
            setOperation.SAdd(key, values.toArray(new String[0]));

            Set<String> members = setOperation.SMembers(key);
            assertEquals(values.size(), members.size());
            for (String value : values) {
                assertEquals(true, members.contains(value));
            }
        });
    }

    @Test
    public void testSMove() {
        Pair<String, ArrayList<String>> pair = KeyGenerator.generateSetString("testSMove1", 3);
        final String key = pair.getFirst();
        final ArrayList<String> values = pair.getSecond();

        Pair<String, ArrayList<String>> pairOther = KeyGenerator.generateSetString("testSMove2", 0);
        final String otherKey = pairOther.getFirst();

        assertDoesNotThrow(() -> {
            setOperation.SAdd(key, values.toArray(new String[0]));

            for (String value : values) {
                assertEquals(true, setOperation.SMove(key, value, otherKey));
            }

            assertEquals(values.size(), setOperation.SMembers(otherKey).size());
        });
    }

    @Test
    public void testSPop() {
        Pair<String, ArrayList<String>> pair = KeyGenerator.generateSetString("testSPop", 3);
        final String key = pair.getFirst();
        final ArrayList<String> values = pair.getSecond();

        assertDoesNotThrow(() -> {
            setOperation.SAdd(key, values.toArray(new String[0]));

            for (int i = 0; i < values.size(); i++) {
                assertEquals(true, values.contains(setOperation.SPop(key)));
            }

            assertEquals(0, setOperation.SCard(key));
        });
    }

    @Test
    public void testSRandMember() {
        Pair<String, ArrayList<String>> pair = KeyGenerator.generateSetString("testSRandMember", 3);
        final String key = pair.getFirst();
        final ArrayList<String> values = pair.getSecond();

        assertDoesNotThrow(() -> {
            setOperation.SAdd(key, values.toArray(new String[0]));

            for (int i = 0; i < values.size(); i++) {
                assertEquals(true, values.contains(setOperation.SRandMember(key)));
            }
        });
    }

    @Test
    public void testSRandMemberCount() {
        Pair<String, ArrayList<String>> pair = KeyGenerator.generateSetString("testSRandMemberCount", 3);
        final String key = pair.getFirst();
        final ArrayList<String> values = pair.getSecond();

        assertDoesNotThrow(() -> {
            setOperation.SAdd(key, values.toArray(new String[0]));

            for (int i = 0; i < values.size(); i++) {
                List<String> sRandMember = setOperation.SRandMember(key, values.size());
                assertEquals(values.size(), sRandMember.size());
                for (String value : sRandMember) {
                    assertEquals(true, values.contains(value));
                }
            }
        });
    }

    @Test
    public void testSRem() {
        Pair<String, ArrayList<String>> pair = KeyGenerator.generateSetString("testSRem", 3);
        final String key = pair.getFirst();
        final ArrayList<String> values = pair.getSecond();

        assertDoesNotThrow(() -> {
            setOperation.SAdd(key, values.toArray(new String[0]));

            assertEquals(values.size(), setOperation.SRem(key, values.toArray(new String[0])));

            assertEquals(0, setOperation.SCard(key));
        });
    }


    @Test
    public void testSUnion() {
        Pair<String, ArrayList<String>> pair = KeyGenerator.generateSetString("testSUnion1", 3);
        final String key = pair.getFirst();
        final ArrayList<String> values = pair.getSecond();

        Pair<String, ArrayList<String>> pairOther = KeyGenerator.generateSetString("testSUnion2", 4);
        final String keyOther = pairOther.getFirst();
        final ArrayList<String> valuesOther = pairOther.getSecond();

        assertDoesNotThrow(() -> {
            setOperation.SAdd(key, values.toArray(new String[0]));
            setOperation.SAdd(keyOther, valuesOther.toArray(new String[0]));
            Set<String> sUnion = setOperation.SUnion(key, keyOther);
            assertEquals(values.size() + valuesOther.size(), sUnion.size());
            for (String value : values) {
                assertEquals(true, sUnion.contains(value));
            }
            for (String value : valuesOther) {
                assertEquals(true, sUnion.contains(value));
            }
        });
    }

    @Test
    public void testSUnionStore() {
        Pair<String, ArrayList<String>> pair = KeyGenerator.generateSetString("testSUnionStore1", 3);
        final String key = pair.getFirst();
        final ArrayList<String> values = pair.getSecond();

        Pair<String, ArrayList<String>> pairOther = KeyGenerator.generateSetString("testSUnionStore2", 4);
        final String otherKey = pairOther.getFirst();
        final ArrayList<String> valuesOther = pairOther.getSecond();

        Pair<String, ArrayList<String>> pairDest = KeyGenerator.generateSetString("testSUnionStore3", 0);
        final String destKey = pairDest.getFirst();

        assertDoesNotThrow(() -> {
            setOperation.SAdd(key, values.toArray(new String[0]));
            setOperation.SAdd(otherKey, valuesOther.toArray(new String[0]));

            assertEquals(values.size() + valuesOther.size(), setOperation.SUnionStore(key, otherKey, destKey));
            assertEquals(values.size() + valuesOther.size(), setOperation.SMembers(destKey).size());
        });
    }
}
