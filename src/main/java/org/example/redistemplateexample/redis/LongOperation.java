package org.example.redistemplateexample.redis;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class LongOperation {

    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    public void Set(String key, Long value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Long Get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Long GetAndSet(String key, Long value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    public Long Increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Long IncrementBy(String key, Long value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    public Long Decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    public Long DecrementBy(String key, Long value) {
        return redisTemplate.opsForValue().decrement(key, value);
    }

}
