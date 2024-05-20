package org.example.redistemplateexample.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.connection.stream.StreamReadOptions;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.List;

@SpringBootTest
public class RedisTemplateListTest {
    @Autowired
    private RedisTemplateList redisTemplateList;

    @SuppressWarnings("null")
    @Test
    public void testGetRedisTemplate() {
        RedisTemplate<String, String> stringRedisTemplate = redisTemplateList.getRedisTemplate(0);
        assertNotNull(stringRedisTemplate);
        String key = "key";
        String value = "value";
        stringRedisTemplate.opsForValue().set(key, value);
        String result = stringRedisTemplate.opsForValue().get(key);
        assertNotNull(result);


        for (int i = 1; i < 80; i++) {
            new Thread(() -> {
                RedisTemplate<String, String> redisTemplate = redisTemplateList.getRedisTemplate(0);
                assertNotNull(redisTemplate);
                StreamReadOptions options = StreamReadOptions.empty().block(Duration.ofSeconds(100)).count(1);
                while (true) {
                    List<MapRecord<String, Object, Object>> records = redisTemplate.opsForStream().read(Consumer.from("group", "consumer"), options, StreamOffset.create("stream", ReadOffset.lastConsumed()));
                    if (!records.isEmpty()) {
                        records.forEach(record -> {
                            System.out.println("Thread " + Thread.currentThread().threadId() + " received: " + record.getValue());
                        });
                    }
                }
            }).start();
        }

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetRedisTemplate1() {
        RedisTemplate<String, String> stringRedisTemplate = redisTemplateList.getRedisTemplate(1);
        assertNotNull(stringRedisTemplate);
        String key = "key";
        String value = "value";
        stringRedisTemplate.opsForValue().set(key, value);
        String result = stringRedisTemplate.opsForValue().get(key);
        assertNotNull(result);
    }
}
