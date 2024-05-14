package org.example.redistemplateexample.redis;

import org.springframework.data.redis.core.RedisTemplate;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BytesOperation {
    @Resource(name = "bytesRedisTemplate")
    private RedisTemplate<String, byte[]> redisTemplate;

    public void Set(String key, byte[] value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public byte[] Get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
