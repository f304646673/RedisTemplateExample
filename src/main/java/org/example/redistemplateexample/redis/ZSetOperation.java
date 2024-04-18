package org.example.redistemplateexample.redis;

import jakarta.annotation.Resource;
import org.springframework.data.redis.connection.zset.Aggregate;
import org.springframework.data.redis.connection.zset.Weights;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class ZSetOperation {

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    
    public boolean ZAdd(String key, String value, double score) {
        try {
            redisTemplate.opsForZSet().add(key, value, score);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Long ZCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    public Long ZCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    public Double ZIncrBy(String key, String value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    public Set<String> ZRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public Set<String> ZRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    public Set<String> ZRangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    public Set<ZSetOperations.TypedTuple<String>> ZRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    public Set<ZSetOperations.TypedTuple<String>> ZRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
    }

    public Set<ZSetOperations.TypedTuple<String>> ZRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    public Long ZRank(String key, String value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    public Long ZRem(String key, String... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    public Long ZRemRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    public Long ZRemRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    public Set<String> ZRevRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public Set<String> ZRevRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    public Set<String> ZRevRangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }

    public Set<ZSetOperations.TypedTuple<String>> ZRevRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    public Set<ZSetOperations.TypedTuple<String>>ZRevRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
    }

    public Set<ZSetOperations.TypedTuple<String>> ZRevRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    public Long ZRevRank(String key, String value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    public Double ZScore(String key, String value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    public Set<String> ZUnion(String key, String otherKey) {
        return redisTemplate.opsForZSet().union(key, otherKey);
    }

    public Set<String> ZUnion(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForZSet().union(key, otherKeys);
    }

    public Long ZUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    public Long ZUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    public Long ZUnionAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey, aggregate);
    }

    public Long ZUnionAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey, aggregate, weights);
    }

    public Set<String> ZInter(String key, String otherKey) {
        return redisTemplate.opsForZSet().intersect(key, otherKey);
    }

    public Set<String> ZInter(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForZSet().intersect(key, otherKeys);
    }

    public Long ZInterAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    public Long ZInterAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }

    public Long ZInterAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey, aggregate);
    }

    public Long ZInterAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey, aggregate, weights);
    }

}
