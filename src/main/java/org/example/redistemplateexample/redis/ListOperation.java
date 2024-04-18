package org.example.redistemplateexample.redis;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ListOperation {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * Removes and returns the first element of the list stored at the specified key,
     * waiting up to the specified timeout if the list is empty.
     *
     * @param key   the key of the list
     * @param count the number of elements to remove and return
     * @return the list of removed elements, or an empty list if the timeout expires
     * @throws Exception if an error occurs while performing the operation
     */
    public List<String> BLPop(String key, long count) throws Exception {
        return redisTemplate.opsForList().leftPop(key, count);
    }

    /**
     * Removes and returns the last element from a list stored at the specified key,
     * blocking until an element is available or the timeout expires.
     *
     * @param key   the key of the list
     * @param count the number of elements to remove and return
     * @return the list of removed elements, or an empty list if the timeout expires
     * @throws Exception if an error occurs while executing the operation
     */
    public List<String> BRPop(String key, long count) throws Exception {
        return redisTemplate.opsForList().rightPop(key, count);
    }

    /**
     * Removes and returns the last element from the list stored at sourceKey, and pushes the element to the front of the list stored at destinationKey.
     *
     * @param sourceKey      the key of the source list
     * @param destinationKey the key of the destination list
     * @return the element being popped and pushed
     * @throws Exception if an error occurs while executing the operation
     */
    public String BRPopLPush(String sourceKey, String destinationKey) throws Exception {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * Removes and returns the last element from the list stored at sourceKey, and pushes the element to the front of the list stored at destinationKey.
     * If the sourceKey is empty, the method blocks until an element is available or the timeout expires.
     * If the timeout expires, null is returned.
     *
     * @param sourceKey      the key of the source list
     * @param destinationKey the key of the destination list
     * @param timeout        the maximum time to wait for an element to be available in the source list
     * @param unit           the time unit of the timeout parameter
     * @return the element being popped and pushed, or null if the timeout expires
     * @throws Exception if an error occurs while performing the operation
     */
    public String BRPopLPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) throws Exception {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
    }

    /**
     * Removes and returns the last element from the list stored at sourceKey, and pushes the element to the front of the list stored at destinationKey.
     * If the sourceKey is empty, the method blocks until an element is available or the timeout expires.
     *
     * @param sourceKey      the key of the source list
     * @param destinationKey the key of the destination list
     * @param timeout        the maximum time to wait for an element to be available in the source list
     * @return the element being popped and pushed
     * @throws Exception if an error occurs while performing the operation
     */
    public String BRPopLPush(String sourceKey, String destinationKey, Duration timeout) throws Exception {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout);
    }

    /**
     * Returns the element at the specified index in the list stored at the given key.
     *
     * @param key   the key of the list
     * @param index the index of the element to retrieve
     * @return the element at the specified index, or null if the key or index is invalid
     * @throws Exception if any error occurs while performing the operation
     */
    public String LIndex(String key, long index) throws Exception {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * Inserts a value into a Redis list at the specified pivot element.
     *
     * @param key the key of the Redis list
     * @param pivot the pivot element in the Redis list
     * @param value the value to be inserted
     * @param before true to insert the value before the pivot, false to insert it after
     * @return the length of the list after the insert operation
     * @throws Exception if an error occurs while performing the operation
     */
    public Long LInsert(String key, String pivot, String value, boolean before) throws Exception {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * Returns the length of the list stored at the specified key.
     *
     * @param key the key of the list
     * @return the length of the list
     * @throws Exception if an error occurs while retrieving the length
     */
    public Long LLen(String key) throws Exception {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * Removes and returns the first element from the list stored at the specified key.
     *
     * @param key the key of the list
     * @return the first element of the list, or null if the list is empty
     * @throws Exception if an error occurs while performing the operation
     */
    public String LPop(String key) throws Exception {
        return redisTemplate.opsForList().leftPop(key);
    }
 
    /**
     * Pushes a value to the left end of a list stored at the specified key.
     *
     * @param key the key of the list
     * @param value the value to be pushed
     * @return the length of the list after the push operation
     * @throws Exception if an error occurs while performing the operation
     */
    public Long LPush(String key, String value) throws Exception {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * Pushes a value to the left of a list, only if the key already exists.
     *
     * @param key   the key of the list
     * @param value the value to be pushed
     * @return the length of the list after the push operation
     * @throws Exception if an error occurs while performing the operation
     */
    public Long LPushX(String key, String value) throws Exception {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * Retrieves a range of elements from a list stored at the specified key.
     *
     * @param key   the key of the list
     * @param start the start index of the range (inclusive)
     * @param end   the end index of the range (inclusive)
     * @return a list containing the specified range of elements
     * @throws Exception if an error occurs while retrieving the range
     */
    public List<String> LRange(String key, long start, long end) throws Exception {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * Removes the specified number of occurrences of the specified value from the list stored at the given key.
     *
     * @param key   the key of the list
     * @param count the number of occurrences to remove
     * @param value the value to be removed
     * @return the number of removed elements
     * @throws Exception if an error occurs while removing the elements
     */
    public Long LRem(String key, long count, String value) throws Exception {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * Sets the value of an element in a list at the specified index.
     *
     * @param key   the key of the list
     * @param index the index of the element to set
     * @param value the new value to set
     * @throws Exception if an error occurs while setting the value
     */
    public void LSet(String key, long index, String value) throws Exception {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * Trims a list stored at the specified key, keeping only the elements within the specified range.
     *
     * @param key   the key of the list
     * @param start the start index of the range (inclusive)
     * @param end   the end index of the range (inclusive)
     * @throws Exception if an error occurs while trimming the list
     */
    public void LTrim(String key, long start, long end) throws Exception {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * Removes and returns the last element from the list stored at the specified key.
     *
     * @param key the key of the list
     * @return the last element from the list, or null if the list is empty or the key does not exist
     * @throws Exception if an error occurs while executing the Redis command
     */
    public String RPop(String key) throws Exception {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * Removes the last element from the list stored at sourceKey, and pushes it to the front of the list stored at destinationKey.
     *
     * @param sourceKey the key of the source list
     * @param destinationKey the key of the destination list
     * @return the element being popped and pushed
     * @throws Exception if an error occurs while executing the operation
     */
    public String RPopLPush(String sourceKey, String destinationKey) throws Exception {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * Pushes a value to the right end of a list stored at the specified key in Redis.
     *
     * @param key   the key of the list in Redis
     * @param value the value to be pushed to the list
     * @return the length of the list after the push operation
     * @throws Exception if any error occurs during the operation
     */
    public Long RPush(String key, String value) throws Exception {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * Pushes a value to the right end of a list if the key already exists.
     *
     * @param key   the key of the list
     * @param value the value to be pushed
     * @return the length of the list after the push operation
     * @throws Exception if an error occurs while performing the operation
     */
    public Long RPushX(String key, String value) throws Exception {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }
    
}
