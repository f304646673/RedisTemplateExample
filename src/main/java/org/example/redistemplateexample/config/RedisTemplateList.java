package org.example.redistemplateexample.config;

import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Component
@DependsOn("redisPool")
public class RedisTemplateList {
    @Resource
    private RedisPool redisPool;

    public <T, T1> RedisTemplate<T, T1> getRedisTemplate(int index) {
        LettuceConnectionFactory lettuceConnectionFactory = redisPool.getRedisConnectionFactorys().get(index);
        if (lettuceConnectionFactory == null) {
            return null;
        }
        RedisTemplate<T, T1> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setValueSerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.string());
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
