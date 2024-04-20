package org.example.redistemplateexample.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;

@Component
public class StringOperation {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public boolean Set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    public String Get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Integer Append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    public Long StrLen(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    public boolean SetNX(String key, String value) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value));
    }


    public boolean SetEX(String key, String value, Duration duration) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, duration));
    }

    public Collection<String> MultiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    public void MultiSet(Map<String, String> kvs) {
        redisTemplate.opsForValue().multiSet(kvs);
    }

    public boolean MultiSetNX(Map<String, String> kvs) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().multiSetIfAbsent(kvs));
    }

    public String GetSet(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    public String GetRange(String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    public void SetRange(String key, String value, long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    public Long BitCount(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    public void MSet(Map<String, String> kvs) {
        redisTemplate.opsForValue().multiSet(kvs);
    }

    public boolean MSetNX(Map<String, String> kvs) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().multiSetIfAbsent(kvs));
    }

}
