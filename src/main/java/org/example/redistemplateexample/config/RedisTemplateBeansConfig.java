package org.example.redistemplateexample.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisTemplateBeansConfig {

    @Bean(name = "bytesRedisTemplate")
    public RedisTemplate<String, byte[]> bytesRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(RedisSerializer.byteArray());
        redisTemplate.setHashValueSerializer(RedisSerializer.byteArray());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "nomaljsonRedisTemplate")
    public RedisTemplate<Object, Object> nomaljsonRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        redisTemplate.setKeySerializer(RedisSerializer.json());
        redisTemplate.setHashKeySerializer(RedisSerializer.json());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "fastjsonRedisTemplate")
    public RedisTemplate<Object, Object> fastjsonRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setKeySerializer(fastJsonRedisSerializer);
        redisTemplate.setHashKeySerializer(fastJsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "iomemoryRedisTemplate")
    public RedisTemplate<Object, Object> iomemoryRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        IoSerializer<Object> objectIoSerializer = new IoSerializer<>();
        redisTemplate.setValueSerializer(objectIoSerializer);
        redisTemplate.setHashValueSerializer(objectIoSerializer);
        redisTemplate.setKeySerializer(objectIoSerializer);
        redisTemplate.setHashKeySerializer(objectIoSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
