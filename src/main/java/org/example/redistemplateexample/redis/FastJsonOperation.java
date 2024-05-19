package org.example.redistemplateexample.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Component
public class FastJsonOperation<T>  {

    @Resource(name = "fastjsonRedisTemplate")
    public RedisTemplate<String, T> redisTemplate;

    public void Set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object Get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
