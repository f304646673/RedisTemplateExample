package org.example.redistemplateexample.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisPoolTest {

    @Autowired
    private RedisPool redisPool;

    @Test
    public void test() {
        System.out.println(redisPool);
    }
}
