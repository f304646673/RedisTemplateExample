package org.example.redistemplateexample.redis;

import jakarta.annotation.Resource;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    public boolean Expire(String key, long timeout, TimeUnit timeUnit) throws Exception{
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, timeUnit));
    }

    public boolean ExpireAt(String key, Date date) throws Exception{
        return Boolean.TRUE.equals(redisTemplate.expireAt(key, date));
    }

    public boolean Persist(String key) throws Exception{
        return Boolean.TRUE.equals(redisTemplate.persist(key));
    }

    public Long TTL(String key) throws Exception{
        return redisTemplate.getExpire(key);
    }

    public String Type(String key) throws Exception{
        return redisTemplate.type(key).code();
    }

    public Collection<String> Keys(String pattern) throws Exception{
        return redisTemplate.keys(pattern);
    }

    public boolean Rename(String key, String newKey) throws Exception{
        redisTemplate.rename(key, newKey);
        return true;
    }

    public boolean RenameNX(String key, String newKey) throws Exception{
        return Boolean.TRUE.equals(redisTemplate.renameIfAbsent(key, newKey));
    }

//    public boolean Sort(String key, String destKey) throws Exception{
//        redisTemplate.sort(key, redisTemplate.getBoundListOps(key), redisTemplate.getBoundListOps(destKey));
//        return true;
//    }


    
}
