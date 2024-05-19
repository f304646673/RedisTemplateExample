package org.example.redistemplateexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;

@Configuration
public class RedisTemplateBeansConfig {

    @Bean(name = "bytesRedisTemplate")
    public RedisTemplate<String, byte[]> bytesRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(RedisSerializer.byteArray());
        redisTemplate.setHashValueSerializer(RedisSerializer.byteArray());
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "nomaljsonRedisTemplate")
    public <T> RedisTemplate<String, T> nomaljsonRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "fastjsonRedisTemplate")
    public RedisTemplate<String, Object> fastjsonRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(new FastJsonRedisSerializer(Object.class));
        redisTemplate.setHashValueSerializer(new FastJsonRedisSerializer(Object.class));
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "iomemoryRedisTemplate")
    public <T> RedisTemplate<String, T> iomemoryRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(new IoSerializer<T>());
        redisTemplate.setHashValueSerializer(new IoSerializer<T>());
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
