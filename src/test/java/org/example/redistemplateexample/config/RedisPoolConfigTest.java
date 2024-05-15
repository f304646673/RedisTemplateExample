package org.example.redistemplateexample.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisPoolConfigTest {
    
    @Autowired
    private RedisPoolConfig redisPoolConfig;

    @Test
    public void test() {
        System.out.println(redisPoolConfig);
    }
}
