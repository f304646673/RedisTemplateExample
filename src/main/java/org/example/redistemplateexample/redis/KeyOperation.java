package org.example.redistemplateexample.redis;

import jakarta.annotation.Resource;

import java.util.Collection;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public final class KeyOperation {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    public Long Del(Collection<String> keys) throws Exception{
        return redisTemplate.delete(keys);
    }

    public boolean Del(String key) throws Exception{
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public boolean Exists(String key) throws Exception{
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    
}
