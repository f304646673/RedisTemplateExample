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
    
    /**
     * Adds a member with a score to a sorted set in Redis.
     *
     * @param key   the key of the sorted set
     * @param value the value of the member to be added
     * @param score the score of the member to be added
     * @return true if the member was added successfully, false otherwise
     */
    public boolean ZAdd(String key, String value, double score) {
        try {
            redisTemplate.opsForZSet().add(key, value, score);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the number of elements in the sorted set stored at the specified key.
     *
     * @param key the key identifying the sorted set
     * @return the number of elements in the sorted set
     */
    public Long ZCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * Returns the count of elements in the sorted set stored at the specified key that have scores between the given minimum and maximum values (inclusive).
     *
     * @param key the key identifying the sorted set
     * @param min the minimum score value
     * @param max the maximum score value
     * @return the count of elements with scores between the minimum and maximum values
     */
    public Long ZCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * Increments the score of a member in a sorted set stored at the specified key by the given delta.
     *
     * @param key   the key of the sorted set
     * @param value the member whose score needs to be incremented
     * @param delta the value by which the score should be incremented
     * @return the new score of the member after incrementing
     */
    public Double ZIncrBy(String key, String value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * Retrieves a range of elements from a sorted set stored at the specified key.
     *
     * @param key   the key of the sorted set
     * @param start the start index of the range (inclusive)
     * @param end   the end index of the range (inclusive)
     * @return a set of elements in the specified range
     */
    public Set<String> ZRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * Retrieves all elements in the sorted set at the specified key with a score between the given minimum and maximum.
     *
     * @param key the key of the sorted set
     * @param min the minimum score (inclusive)
     * @param max the maximum score (inclusive)
     * @return a set of elements in the specified score range
     */
    public Set<String> ZRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * Retrieves a range of elements from a sorted set stored at the specified key, based on the scores.
     *
     * @param key    the key of the sorted set
     * @param min    the minimum score (inclusive)
     * @param max    the maximum score (inclusive)
     * @param offset the index offset of the first element to return
     * @param count  the maximum number of elements to return
     * @return a set of elements within the specified score range
     */
    public Set<String> ZRangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    /**
     * Retrieves all elements in the sorted set at the specified key with scores between the given minimum and maximum.
     *
     * @param key the key of the sorted set
     * @param min the minimum score (inclusive)
     * @param max the maximum score (inclusive)
     * @return a set of elements with their respective scores
     */
    public Set<ZSetOperations.TypedTuple<String>> ZRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * Retrieves a range of elements from a sorted set stored at the specified key, where the score is within the given range (inclusive),
     * along with their scores.
     *
     * @param key    the key of the sorted set
     * @param min    the minimum score (inclusive)
     * @param max    the maximum score (inclusive)
     * @param offset the index of the first element to return
     * @param count  the maximum number of elements to return
     * @return a set of elements with their scores, within the specified score range
     */
    public Set<ZSetOperations.TypedTuple<String>> ZRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * Retrieves a range of elements from a sorted set stored at the specified key,
     * along with their scores.
     *
     * @param key   the key of the sorted set
     * @param start the start index of the range
     * @param end   the end index of the range
     * @return a set of elements with their scores within the specified range
     */
    public Set<ZSetOperations.TypedTuple<String>> ZRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * Returns the rank of a member in a sorted set stored at the specified key.
     *
     * @param key   the key of the sorted set
     * @param value the member whose rank is to be returned
     * @return the rank of the member or null if the member does not exist in the sorted set
     */
    public Long ZRank(String key, String value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * Removes the specified values from the sorted set stored at the given key.
     *
     * @param key    the key of the sorted set
     * @param values the values to be removed from the sorted set
     * @return the number of values removed from the sorted set
     */
    public Long ZRem(String key, String... values) {
        return redisTemplate.opsForZSet().remove(key, (Object[])values);
    }

    /**
     * Removes all elements in the sorted set stored at the specified key with rank between start and end.
     *
     * @param key   the key of the sorted set
     * @param start the start rank
     * @param end   the end rank
     * @return the number of elements removed from the sorted set
     */
    public Long ZRemRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * Removes all elements in the sorted set stored at the specified key with scores between the given min and max.
     * The elements are removed in the range [min, max].
     *
     * @param key the key of the sorted set
     * @param min the minimum score
     * @param max the maximum score
     * @return the number of elements removed from the sorted set
     */
    public Long ZRemRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * Retrieves a range of elements from a sorted set stored at the specified key,
     * ordered from highest to lowest score.
     *
     * @param key   the key identifying the sorted set
     * @param start the start index of the range (inclusive)
     * @param end   the end index of the range (inclusive)
     * @return a set of elements within the specified range
     */
    public Set<String> ZRevRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * Retrieves all elements in the sorted set at the specified key with scores between the given minimum and maximum values (inclusive),
     * in descending order.
     *
     * @param key the key of the sorted set
     * @param min the minimum score
     * @param max the maximum score
     * @return a set of elements in descending order by score
     */
    public Set<String> ZRevRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * Retrieves a range of elements from a sorted set stored at the specified key,
     * with scores between the given minimum and maximum values (inclusive), in reverse order.
     * 
     * @param key    the key of the sorted set
     * @param min    the minimum score value
     * @param max    the maximum score value
     * @param offset the index of the first element to return
     * @param count  the maximum number of elements to return
     * @return a set of elements in reverse order within the specified score range
     */
    public Set<String> ZRevRangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }

    /**
     * Retrieves a range of elements from a sorted set stored at the specified key,
     * with scores within the given range (inclusive), in reverse order, along with their scores.
     *
     * @param key the key of the sorted set
     * @param min the minimum score value
     * @param max the maximum score value
     * @return a set of elements with their scores within the specified range, in reverse order
     */
    public Set<ZSetOperations.TypedTuple<String>> ZRevRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * Retrieves a range of elements from a sorted set stored at the specified key,
     * with scores within the given range (inclusive), in reverse order, along with their scores.
     *
     * @param key    the key of the sorted set
     * @param min    the minimum score (inclusive)
     * @param max    the maximum score (inclusive)
     * @param offset the index of the first element to return
     * @param count  the maximum number of elements to return
     * @return a set of elements with their scores, in reverse order
     */
    public Set<ZSetOperations.TypedTuple<String>>ZRevRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * Retrieves a range of elements from a sorted set stored at the specified key,
     * ordered by score in descending order, along with their scores.
     *
     * @param key   the key identifying the sorted set
     * @param start the start index of the range (inclusive)
     * @param end   the end index of the range (inclusive)
     * @return a set of elements with their scores, ordered by score in descending order
     */
    public Set<ZSetOperations.TypedTuple<String>> ZRevRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * Returns the reverse rank of a value in a sorted set stored at the specified key.
     *
     * @param key the key of the sorted set
     * @param value the value whose reverse rank needs to be determined
     * @return the reverse rank of the value, or null if the value is not present in the sorted set
     */
    public Long ZRevRank(String key, String value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * Retrieves the score associated with the specified value in the sorted set stored at the given key.
     *
     * @param key   the key of the sorted set
     * @param value the value whose score needs to be retrieved
     * @return the score associated with the value, or null if the value is not present in the sorted set
     */
    public Double ZScore(String key, String value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * Performs a union operation on the sorted sets stored at key and otherKey, and returns the resulting set.
     *
     * @param key the key of the first sorted set
     * @param otherKey the key of the second sorted set
     * @return the resulting set of the union operation
     */
    public Set<String> ZUnion(String key, String otherKey) {
        return redisTemplate.opsForZSet().union(key, otherKey);
    }

    /**
     * Performs a union operation on sorted sets stored at the specified key and other keys,
     * and returns the resulting set.
     *
     * @param key       the key of the sorted set to perform the union operation on
     * @param otherKeys the collection of keys of other sorted sets to include in the union operation
     * @return the resulting set after performing the union operation
     */
    public Set<String> ZUnion(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForZSet().union(key, otherKeys);
    }

    /**
     * Performs a union operation on sorted sets and stores the result in a new sorted set.
     *
     * @param key the key of the first sorted set
     * @param otherKey the key of the second sorted set
     * @param destKey the key of the destination sorted set where the result will be stored
     * @return the number of elements in the resulting sorted set
     */
    public Long ZUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * Performs a union operation on sorted sets at the specified key and other keys, and stores the result in a new sorted set at the destination key.
     * 
     * @param key the key of the sorted set to perform the union operation on
     * @param otherKeys the collection of keys of other sorted sets to include in the union operation
     * @param destKey the key of the destination sorted set where the result will be stored
     * @return the number of elements in the resulting sorted set
     */
    public Long ZUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * Performs a union operation on sorted sets at the specified key and other keys, and stores the result in a sorted set at the destination key.
     * 
     * @param key the key of the sorted set to perform the union operation on
     * @param otherKeys the collection of keys of other sorted sets to include in the union operation
     * @param destKey the key of the sorted set to store the result of the union operation
     * @param aggregate the aggregation function to apply when multiple elements with the same score are encountered
     * @return the number of elements in the resulting sorted set at the destination key
     */
    public Long ZUnionAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey, aggregate);
    }

    /**
     * Performs a union operation on sorted sets and stores the result in a new sorted set.
     *
     * @param key the key of the sorted set to perform the union operation on
     * @param otherKeys the keys of the other sorted sets to include in the union operation
     * @param destKey the key of the sorted set to store the result in
     * @param aggregate the aggregation function to apply in case of a tie between elements with the same score
     * @param weights the weights to apply to the sorted sets before performing the union operation
     * @return the number of elements in the resulting sorted set
     */
    public Long ZUnionAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey, aggregate, weights);
    }

    /**
     * Computes the intersection of sorted sets stored at the specified key and otherKey, and returns the resulting set.
     *
     * @param key the key of the first sorted set
     * @param otherKey the key of the second sorted set
     * @return the set containing the elements that exist in both sorted sets
     */
    public Set<String> ZInter(String key, String otherKey) {
        return redisTemplate.opsForZSet().intersect(key, otherKey);
    }

    /**
     * Computes the intersection of sorted sets stored at the specified key and other given keys,
     * and returns the resulting set.
     *
     * @param key       the key of the sorted set to intersect
     * @param otherKeys the collection of keys of the other sorted sets to intersect with
     * @return the set containing the elements that exist in all the specified sorted sets
     */
    public Set<String> ZInter(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForZSet().intersect(key, otherKeys);
    }

    /**
     * Computes the intersection of sorted sets stored at the specified key and otherKey, and stores the result in the destination key.
     * 
     * @param key the key of the first sorted set
     * @param otherKey the key of the second sorted set
     * @param destKey the key where the result of the intersection will be stored
     * @return the number of elements in the resulting sorted set
     */
    public Long ZInterAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * Performs a ZSET intersection between the sorted sets stored at the specified key and the other specified keys,
     * and stores the result in a new sorted set at the destination key.
     *
     * @param key       the key of the sorted set to intersect
     * @param otherKeys the keys of the other sorted sets to intersect with
     * @param destKey   the key of the destination sorted set to store the intersection result
     * @return the number of elements in the resulting sorted set at the destination key
     */
    public Long ZInterAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * Performs a ZSET intersection between the sorted sets stored at the specified key and the other specified keys,
     * and stores the result in a new sorted set at the destination key.
     *
     * @param key       the key of the sorted set to intersect
     * @param otherKeys the keys of the other sorted sets to intersect with
     * @param destKey   the key of the destination sorted set to store the result
     * @param aggregate the aggregation function to apply when multiple elements with the same score are encountered
     * @return the number of elements in the resulting sorted set at the destination key
     */
    public Long ZInterAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey, aggregate);
    }

    /**
     * Intersects multiple sorted sets and stores the result in a new sorted set.
     *
     * @param key the key of the sorted set to intersect with
     * @param otherKeys the keys of the other sorted sets to intersect with
     * @param destKey the key of the destination sorted set where the result will be stored
     * @param aggregate the aggregation function to apply when multiple sorted sets are intersected
     * @param weights the weights to apply to the sorted sets before intersecting them
     * @return the number of elements in the resulting sorted set
     */
    public Long ZInterAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey, aggregate, weights);
    }

}
