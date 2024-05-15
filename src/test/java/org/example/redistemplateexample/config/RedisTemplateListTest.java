package org.example.redistemplateexample.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RedisTemplateListTest {
    @Autowired
    private RedisTemplateList redisTemplateList;

    @Test
    public void testGetRedisTemplate() {
        RedisTemplate<String, String> stringRedisTemplate = redisTemplateList.getRedisTemplate(0);
        assertNotNull(stringRedisTemplate);
        stringRedisTemplate.opsForValue().set("key", "value");
    }
}
