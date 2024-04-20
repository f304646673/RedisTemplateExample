package org.example.redistemplateexample.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

@SpringBootTest
public class ZSetOperationTest {

    @Autowired
    private ZSetOperation zSetOperation;

    @Test
    public void testZAdd() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator.generateZSetString("testAdd", 1);
        final String key = pair.getFirst();
        final String member = pair.getSecond().getFirst().getFirst();
        final double score = pair.getSecond().getFirst().getSecond();

        assertEquals(true, zSetOperation.ZAdd(key, member, score));
    }

    @Test
    public void testZCard() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator.generateZSetString("testCard", 1);
        final String key = pair.getFirst();
        final String member = pair.getSecond().getFirst().getFirst();
        final double score = pair.getSecond().getFirst().getSecond();

        assertEquals(0, zSetOperation.ZCard(key));

        zSetOperation.ZAdd(key, member, score);
        assertEquals(1, zSetOperation.ZCard(key));
    }

    @Test
    public void testZCount() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator.generateZSetString("testCount", 1);
        final String key = pair.getFirst();
        final String member = pair.getSecond().getFirst().getFirst();
        final double score = pair.getSecond().getFirst().getSecond();

        assertEquals(0, zSetOperation.ZCount(key, 0, 1));

        zSetOperation.ZAdd(key, member, score);
        assertEquals(1, zSetOperation.ZCount(key, 0, 1));
    }

    @Test
    public void testZIncrBy() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator.generateZSetString("testIncrBy", 1);
        final String key = pair.getFirst();
        final String member = pair.getSecond().getFirst().getFirst();
        final double score = pair.getSecond().getFirst().getSecond();

        assertEquals(0, zSetOperation.ZIncrBy(key, member, score));
        assertEquals(score, zSetOperation.ZIncrBy(key, member, score));
    }

    @Test
    public void testZRange() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator.generateZSetString("testRange", 3);
        final String key = pair.getFirst();
        final String member1 = pair.getSecond().get(0).getFirst();
        final double score1 = pair.getSecond().get(0).getSecond();
        final String member2 = pair.getSecond().get(1).getFirst();
        final double score2 = pair.getSecond().get(1).getSecond();
        final String member3 = pair.getSecond().get(2).getFirst();
        final double score3 = pair.getSecond().get(2).getSecond();

        zSetOperation.ZAdd(key, member1, score1);
        zSetOperation.ZAdd(key, member2, score2);
        zSetOperation.ZAdd(key, member3, score3);

        assertEquals(3, zSetOperation.ZRange(key, 0, 2).size());
    }

    @Test
    public void testZRangeByScore() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator.generateZSetString("testRangeByScore", 3);
        final String key = pair.getFirst();
        final String member1 = pair.getSecond().get(0).getFirst();
        final double score1 = pair.getSecond().get(0).getSecond();
        final String member2 = pair.getSecond().get(1).getFirst();
        final double score2 = pair.getSecond().get(1).getSecond();
        final String member3 = pair.getSecond().get(2).getFirst();
        final double score3 = pair.getSecond().get(2).getSecond();

        zSetOperation.ZAdd(key, member1, score1);
        zSetOperation.ZAdd(key, member2, score2);
        zSetOperation.ZAdd(key, member3, score3);

        final double min = Math.min(score1, Math.min(score2, score3));
        final double max = Math.max(score1, Math.max(score2, score3));

        assertEquals(3, zSetOperation.ZRangeByScore(key, min, max).size());
    }

    @Test
    public void testZRangeByScoreWithScores() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator
                .generateZSetString("testRangeByScoreWithScores", 3);
        final String key = pair.getFirst();
        final String member1 = pair.getSecond().get(0).getFirst();
        final double score1 = pair.getSecond().get(0).getSecond();
        final String member2 = pair.getSecond().get(1).getFirst();
        final double score2 = pair.getSecond().get(1).getSecond();
        final String member3 = pair.getSecond().get(2).getFirst();
        final double score3 = pair.getSecond().get(2).getSecond();

        zSetOperation.ZAdd(key, member1, score1);
        zSetOperation.ZAdd(key, member2, score2);
        zSetOperation.ZAdd(key, member3, score3);

        final double min = Math.min(score1, Math.min(score2, score3));
        final double max = Math.max(score1, Math.max(score2, score3));

        assertEquals(3, zSetOperation.ZRangeByScoreWithScores(key, min, max).size());
    }

    @Test
    public void testZRank() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator.generateZSetString("testRank", 3);
        final String key = pair.getFirst();
        final String member1 = pair.getSecond().get(0).getFirst();
        final double score1 = pair.getSecond().get(0).getSecond();
        final String member2 = pair.getSecond().get(1).getFirst();
        final double score2 = pair.getSecond().get(1).getSecond();
        final String member3 = pair.getSecond().get(2).getFirst();
        final double score3 = pair.getSecond().get(2).getSecond();

        zSetOperation.ZAdd(key, member1, score1);
        zSetOperation.ZAdd(key, member2, score2);
        zSetOperation.ZAdd(key, member3, score3);

        assertEquals(0, zSetOperation.ZRank(key, member1));
        assertEquals(1, zSetOperation.ZRank(key, member2));
        assertEquals(2, zSetOperation.ZRank(key, member3));
    }

    @Test
    public void testZRem() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator.generateZSetString("testRem", 3);
        final String key = pair.getFirst();
        final String member1 = pair.getSecond().get(0).getFirst();
        final double score1 = pair.getSecond().get(0).getSecond();
        final String member2 = pair.getSecond().get(1).getFirst();
        final double score2 = pair.getSecond().get(1).getSecond();
        final String member3 = pair.getSecond().get(2).getFirst();
        final double score3 = pair.getSecond().get(2).getSecond();

        zSetOperation.ZAdd(key, member1, score1);
        zSetOperation.ZAdd(key, member2, score2);
        zSetOperation.ZAdd(key, member3, score3);

        assertEquals(3, zSetOperation.ZRem(key, member1, member2, member3));
    }

    @Test
    public void testZRemRange() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator.generateZSetString("testRemRange", 3);
        final String key = pair.getFirst();
        final String member1 = pair.getSecond().get(0).getFirst();
        final double score1 = pair.getSecond().get(0).getSecond();
        final String member2 = pair.getSecond().get(1).getFirst();
        final double score2 = pair.getSecond().get(1).getSecond();
        final String member3 = pair.getSecond().get(2).getFirst();
        final double score3 = pair.getSecond().get(2).getSecond();

        zSetOperation.ZAdd(key, member1, score1);
        zSetOperation.ZAdd(key, member2, score2);
        zSetOperation.ZAdd(key, member3, score3);

        assertEquals(3, zSetOperation.ZRemRange(key, 0, 2));
    }

    @Test
    public void testZRemRangeByScore() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator.generateZSetString("testRemRangeByScore", 3);
        final String key = pair.getFirst();
        final String member1 = pair.getSecond().get(0).getFirst();
        final double score1 = pair.getSecond().get(0).getSecond();
        final String member2 = pair.getSecond().get(1).getFirst();
        final double score2 = pair.getSecond().get(1).getSecond();
        final String member3 = pair.getSecond().get(2).getFirst();
        final double score3 = pair.getSecond().get(2).getSecond();

        zSetOperation.ZAdd(key, member1, score1);
        zSetOperation.ZAdd(key, member2, score2);
        zSetOperation.ZAdd(key, member3, score3);

        final double min = Math.min(score1, Math.min(score2, score3));
        final double max = Math.max(score1, Math.max(score2, score3));

        assertEquals(3, zSetOperation.ZRemRangeByScore(key, min, max));
    }

    @Test
    public void testZRevRange() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator.generateZSetString("testRevRange", 3);
        final String key = pair.getFirst();
        final String member1 = pair.getSecond().get(0).getFirst();
        final double score1 = pair.getSecond().get(0).getSecond();
        final String member2 = pair.getSecond().get(1).getFirst();
        final double score2 = pair.getSecond().get(1).getSecond();
        final String member3 = pair.getSecond().get(2).getFirst();
        final double score3 = pair.getSecond().get(2).getSecond();

        zSetOperation.ZAdd(key, member1, score1);
        zSetOperation.ZAdd(key, member2, score2);
        zSetOperation.ZAdd(key, member3, score3);

        assertEquals(3, zSetOperation.ZRevRange(key, 0, 2).size());
    }

    @Test
    public void testZRevRangeByScore() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator.generateZSetString("testRevRangeByScore", 3);
        final String key = pair.getFirst();
        final String member1 = pair.getSecond().get(0).getFirst();
        final double score1 = pair.getSecond().get(0).getSecond();
        final String member2 = pair.getSecond().get(1).getFirst();
        final double score2 = pair.getSecond().get(1).getSecond();
        final String member3 = pair.getSecond().get(2).getFirst();
        final double score3 = pair.getSecond().get(2).getSecond();

        zSetOperation.ZAdd(key, member1, score1);
        zSetOperation.ZAdd(key, member2, score2);
        zSetOperation.ZAdd(key, member3, score3);

        final double min = Math.min(score1, Math.min(score2, score3));
        final double max = Math.max(score1, Math.max(score2, score3));

        assertEquals(3, zSetOperation.ZRevRangeByScore(key, min, max).size());
    }

    @Test
    public void testZRevRangeByScoreWithScores() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator
                .generateZSetString("testRevRangeByScoreWithScores", 3);
        final String key = pair.getFirst();
        final String member1 = pair.getSecond().get(0).getFirst();
        final double score1 = pair.getSecond().get(0).getSecond();
        final String member2 = pair.getSecond().get(1).getFirst();
        final double score2 = pair.getSecond().get(1).getSecond();
        final String member3 = pair.getSecond().get(2).getFirst();
        final double score3 = pair.getSecond().get(2).getSecond();

        zSetOperation.ZAdd(key, member1, score1);
        zSetOperation.ZAdd(key, member2, score2);
        zSetOperation.ZAdd(key, member3, score3);

        final double min = Math.min(score1, Math.min(score2, score3));
        final double max = Math.max(score1, Math.max(score2, score3));

        assertEquals(3, zSetOperation.ZRevRangeByScoreWithScores(key, min, max).size());
    }

    @Test
    public void testZRevRank() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator.generateZSetString("testRevRank", 3);
        final String key = pair.getFirst();
        final String member1 = pair.getSecond().get(0).getFirst();
        final double score1 = pair.getSecond().get(0).getSecond();
        final String member2 = pair.getSecond().get(1).getFirst();
        final double score2 = pair.getSecond().get(1).getSecond();
        final String member3 = pair.getSecond().get(2).getFirst();
        final double score3 = pair.getSecond().get(2).getSecond();

        zSetOperation.ZAdd(key, member1, score1);
        zSetOperation.ZAdd(key, member2, score2);
        zSetOperation.ZAdd(key, member3, score3);

        assertEquals(2, zSetOperation.ZRevRank(key, member1));
        assertEquals(1, zSetOperation.ZRevRank(key, member2));
        assertEquals(0, zSetOperation.ZRevRank(key, member3));
    }

    @Test
    public void testZScore() {
        Pair<String, ArrayList<Pair<String, Double>>> pair = KeyGenerator.generateZSetString("testScore", 1);
        final String key = pair.getFirst();
        final String member = pair.getSecond().getFirst().getFirst();
        final double score = pair.getSecond().getFirst().getSecond();

        zSetOperation.ZAdd(key, member, score);
        assertEquals(score, zSetOperation.ZScore(key, member));
    }

    @Test
    public void testZUnion() {
        Pair<String, ArrayList<Pair<String, Double>>> pair1 = KeyGenerator.generateZSetString("testUnion1", 3);
        final String key1 = pair1.getFirst();
        final String member1 = pair1.getSecond().get(0).getFirst();
        final double score1 = pair1.getSecond().get(0).getSecond();
        final String member2 = pair1.getSecond().get(1).getFirst();
        final double score2 = pair1.getSecond().get(1).getSecond();
        final String member3 = pair1.getSecond().get(2).getFirst();
        final double score3 = pair1.getSecond().get(2).getSecond();

        Pair<String, ArrayList<Pair<String, Double>>> pair2 = KeyGenerator.generateZSetString("testUnion2", 3);
        final String key2 = pair2.getFirst();
        final String member4 = pair2.getSecond().get(0).getFirst();
        final double score4 = pair2.getSecond().get(0).getSecond();
        final String member5 = pair2.getSecond().get(1).getFirst();
        final double score5 = pair2.getSecond().get(1).getSecond();
        final String member6 = pair2.getSecond().get(2).getFirst();
        final double score6 = pair2.getSecond().get(2).getSecond();

        zSetOperation.ZAdd(key1, member1, score1);
        zSetOperation.ZAdd(key1, member2, score2);
        zSetOperation.ZAdd(key1, member3, score3);

        zSetOperation.ZAdd(key2, member4, score4);
        zSetOperation.ZAdd(key2, member5, score5);
        zSetOperation.ZAdd(key2, member6, score6);

        assertEquals(6, zSetOperation.ZUnion(key1, key2).size());
    }

    @Test
    public void testZUnionAndStore() {
        Pair<String, ArrayList<Pair<String, Double>>> pair1 = KeyGenerator.generateZSetString("testUnionAndStore1", 3);
        final String key1 = pair1.getFirst();
        final String member1 = pair1.getSecond().get(0).getFirst();
        final double score1 = pair1.getSecond().get(0).getSecond();
        final String member2 = pair1.getSecond().get(1).getFirst();
        final double score2 = pair1.getSecond().get(1).getSecond();
        final String member3 = pair1.getSecond().get(2).getFirst();
        final double score3 = pair1.getSecond().get(2).getSecond();

        Pair<String, ArrayList<Pair<String, Double>>> pair2 = KeyGenerator.generateZSetString("testUnionAndStore2", 3);
        final String key2 = pair2.getFirst();
        final String member4 = pair2.getSecond().get(0).getFirst();
        final double score4 = pair2.getSecond().get(0).getSecond();
        final String member5 = pair2.getSecond().get(1).getFirst();
        final double score5 = pair2.getSecond().get(1).getSecond();
        final String member6 = pair2.getSecond().get(2).getFirst();
        final double score6 = pair2.getSecond().get(2).getSecond();

        final String destKey = "testUnionAndStoreDest";

        zSetOperation.ZAdd(key1, member1, score1);
        zSetOperation.ZAdd(key1, member2, score2);
        zSetOperation.ZAdd(key1, member3, score3);

        zSetOperation.ZAdd(key2, member4, score4);
        zSetOperation.ZAdd(key2, member5, score5);
        zSetOperation.ZAdd(key2, member6, score6);

        assertEquals(6, zSetOperation.ZUnionAndStore(key1, key2, destKey));
    }

    @Test
    public void testZInter() {
        Pair<String, ArrayList<Pair<String, Double>>> pair1 = KeyGenerator.generateZSetString("testInter1", 3);
        final String key1 = pair1.getFirst();
        final String member1 = pair1.getSecond().get(0).getFirst();
        final double score1 = pair1.getSecond().get(0).getSecond();
        final String member2 = pair1.getSecond().get(1).getFirst();
        final double score2 = pair1.getSecond().get(1).getSecond();
        final String member3 = pair1.getSecond().get(2).getFirst();
        final double score3 = pair1.getSecond().get(2).getSecond();

        Pair<String, ArrayList<Pair<String, Double>>> pair2 = KeyGenerator.generateZSetString("testInter2", 3);
        final String key2 = pair2.getFirst();
        final String member4 = pair2.getSecond().get(0).getFirst();
        final double score4 = pair2.getSecond().get(0).getSecond();
        final String member5 = pair2.getSecond().get(1).getFirst();
        final double score5 = pair2.getSecond().get(1).getSecond();
        final String member6 = pair2.getSecond().get(2).getFirst();
        final double score6 = pair2.getSecond().get(2).getSecond();

        zSetOperation.ZAdd(key1, member1, score1);
        zSetOperation.ZAdd(key1, member2, score2);
        zSetOperation.ZAdd(key1, member3, score3);

        zSetOperation.ZAdd(key2, member4, score4);
        zSetOperation.ZAdd(key2, member5, score5);
        zSetOperation.ZAdd(key2, member6, score6);

        assertEquals(0, zSetOperation.ZInter(key1, key2).size());
    }

    @Test
    public void testZInterAndStore() {
        Pair<String, ArrayList<Pair<String, Double>>> pair1 = KeyGenerator.generateZSetString("testInterAndStore1", 3);
        final String key1 = pair1.getFirst();
        final String member1 = pair1.getSecond().get(0).getFirst();
        final double score1 = pair1.getSecond().get(0).getSecond();
        final String member2 = pair1.getSecond().get(1).getFirst();
        final double score2 = pair1.getSecond().get(1).getSecond();
        final String member3 = pair1.getSecond().get(2).getFirst();
        final double score3 = pair1.getSecond().get(2).getSecond();

        Pair<String, ArrayList<Pair<String, Double>>> pair2 = KeyGenerator.generateZSetString("testInterAndStore2", 3);
        final String key2 = pair2.getFirst();
        final String member4 = pair2.getSecond().get(0).getFirst();
        final double score4 = pair2.getSecond().get(0).getSecond();
        final String member5 = pair2.getSecond().get(1).getFirst();
        final double score5 = pair2.getSecond().get(1).getSecond();
        final String member6 = pair2.getSecond().get(2).getFirst();
        final double score6 = pair2.getSecond().get(2).getSecond();

        final String destKey = "testInterAndStoreDest";

        zSetOperation.ZAdd(key1, member1, score1);
        zSetOperation.ZAdd(key1, member2, score2);
        zSetOperation.ZAdd(key1, member3, score3);

        zSetOperation.ZAdd(key2, member4, score4);
        zSetOperation.ZAdd(key2, member5, score5);
        zSetOperation.ZAdd(key2, member6, score6);

        assertEquals(0, zSetOperation.ZInterAndStore(key1, key2, destKey));
    }

}
