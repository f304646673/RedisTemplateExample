package org.example.redistemplateexample.redis;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListOperation {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public Long LPush(String key, String value) throws Exception {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    public Long RPush(String key, String value) throws Exception {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public String LPop(String key) throws Exception {
        return redisTemplate.opsForList().leftPop(key);
    }

    public String RPop(String key) throws Exception {
        return redisTemplate.opsForList().rightPop(key);
    }

//    public String LLen(String key) throws Exception {
//        return redisTemplate.opsForList().size(key);
//    }
//
    public String LIndex(String key, long index) throws Exception {
        return redisTemplate.opsForList().index(key, index);
    }

    public void LSet(String key, long index, String value) throws Exception {
        redisTemplate.opsForList().set(key, index, value);
    }

    public void LTrim(String key, long start, long end) throws Exception {
        redisTemplate.opsForList().trim(key, start, end);
    }

    public Long LRem(String key, long count, String value) throws Exception {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    public String RPopLPush(String sourceKey, String destinationKey) throws Exception {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    public List<String> BLPop(String key, long count) throws Exception {
        return redisTemplate.opsForList().leftPop(key, count);
    }

    public List<String> BRPop(String key, long count) throws Exception {
        return redisTemplate.opsForList().rightPop(key, count);
    }

    public List<String> LRange(String key, long start, long end) throws Exception {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public Long LInsert(String key, String pivot, String value, boolean before) throws Exception {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    
}
