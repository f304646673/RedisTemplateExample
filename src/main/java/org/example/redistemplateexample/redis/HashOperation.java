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

    public void HSet(String key, String field, String value) throws Exception {
        redisTemplate.opsForHash().put(key, field, value);
    }

    public String HGet(String key, String field) throws Exception {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    public Long HDel(String key, String field) throws Exception {
        return redisTemplate.opsForHash().delete(key, field);
    }

    public boolean HExists(String key, String field) throws Exception {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    public Map<Object, Object> HGetAll(String key) throws Exception {
        return redisTemplate.opsForHash().entries(key);
    }

    public Set<Object> HKeys(String key) throws Exception {
        return redisTemplate.opsForHash().keys(key);
    }

    public Long HLen(String key) throws Exception {
        return redisTemplate.opsForHash().size(key);
    }

    public List<Object> HValues(String key) throws Exception {
        return redisTemplate.opsForHash().values(key);
    }

    public void HSetNX(String key, String field, String value) throws Exception {
        redisTemplate.opsForHash().putIfAbsent(key, field, value);
    }

    public void HMSet(String key, Map<String, String> kvs) throws Exception {
        redisTemplate.opsForHash().putAll(key, kvs);
    }

    public List<Object> HMGet(String key, List<Object> fields) throws Exception {
        return redisTemplate.opsForHash().multiGet(key, fields);
    }

    public Long HIncrBy(String key, String field, long increment) throws Exception {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    public Double HIncrByFloat(String key, String field, double increment) throws Exception {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }



}
