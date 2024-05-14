package org.example.redistemplateexample.redis;

import org.springframework.data.redis.core.RedisTemplate;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class NomalJsonOperation<T> {
    @Resource(name = "nomaljsonRedisTemplate")
    public RedisTemplate<T, T> redisTemplate;

    public void Set(T key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public T Get(T key) {
        return redisTemplate.opsForValue().get(key);
    }
}
