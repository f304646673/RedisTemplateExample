package org.example.redistemplateexample.redis;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class HashOperation {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * Deletes a hash field from a Redis key.
     *
     * @param key   the Redis key
     * @param field the field to delete
     * @return the number of fields deleted
     */
    public Long HDel(String key, String field) {
        return redisTemplate.opsForHash().delete(key, field);
    }

    /**
     * Checks if a field exists in the hash stored at the given key.
     *
     * @param key   the key of the hash
     * @param field the field to check for existence
     * @return true if the field exists, false otherwise
     */
    public boolean HExists(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * Retrieves the value associated with the specified field in the hash stored at
     * the given key.
     *
     * @param key   the key of the hash
     * @param field the field within the hash
     * @return the value associated with the field, or null if either the key or the
     *         field does not exist
     */
    public String HGet(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    /**
     * Retrieves all the fields and values from a hash stored at the specified key.
     *
     * @param key the key of the hash
     * @return a map containing all the fields and values from the hash
     */
    public Map<Object, Object> HGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * Increments the value of a hash field by the given increment.
     *
     * @param key       the key of the hash
     * @param field     the field within the hash
     * @param increment the increment value
     * @return the new value after incrementing the field
     */
    public Long HIncrBy(String key, String field, long increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * Increments the value of a hash field by the given increment.
     *
     * @param key       the key of the hash
     * @param field     the field within the hash
     * @param increment the increment value
     * @return the new value after incrementing, as a {@code Double}
     */
    public Double HIncrByFloat(String key, String field, double increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * Retrieves all the field names (keys) from a hash stored at the specified key.
     *
     * @param key the key of the hash
     * @return a set containing all the field names (keys) from the hash
     */
    public Set<Object> HKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * Returns the number of fields contained in the hash stored at the specified
     * key.
     *
     * @param key the key of the hash
     * @return the number of fields in the hash, or null if the key does not exist
     */
    public Long HLen(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * Retrieves the values associated with the specified fields from the hash
     * stored at the given key.
     *
     * @param key    the key of the hash
     * @param fields the fields to retrieve values for
     * @return a list of values associated with the specified fields, in the same
     *         order as the fields
     */
    public List<Object> HMGet(String key, List<Object> fields) {
        return redisTemplate.opsForHash().multiGet(key, fields);
    }

    /**
     * Sets the specified fields to their respective values in the hash stored at
     * key.
     *
     * @param key the key of the hash
     * @param kvs a map containing the field-value pairs to be set
     */
    public void HMSet(String key, Map<String, String> kvs) {
        redisTemplate.opsForHash().putAll(key, kvs);
    }

    /**
     * Sets the value of a field in the hash stored at the specified key.
     *
     * @param key   the key of the hash
     * @param field the field within the hash
     * @param value the value to be set
     */
    public void HSet(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * Sets the value of a hash field, only if the field does not exist.
     *
     * @param key   the key of the hash
     * @param field the field within the hash
     * @param value the value to set
     * @return {@code true} if the field was set, {@code false} if the field already
     *         exists
     */
    public Boolean HSetNX(String key, String field, String value) {
        return redisTemplate.opsForHash().putIfAbsent(key, field, value);
    }

    /**
     * Returns all values associated with the given hash `key`.
     *
     * @param key the key of the hash
     * @return a list containing all values associated with the hash `key`
     */
    public List<Object> HVals(String key) {
        return redisTemplate.opsForHash().values(key);
    }

}
