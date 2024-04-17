package org.example.redistemplateexample.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Component
public class StringOperation {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public boolean Set(String key, String value) throws Exception {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    public String Get(String key) throws Exception {
        return redisTemplate.opsForValue().get(key);
    }

}
