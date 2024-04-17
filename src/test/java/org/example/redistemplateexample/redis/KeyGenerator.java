package org.example.redistemplateexample.redis;

import org.springframework.data.util.Pair;

public class KeyGenerator {
    public static Pair<String, String> generate(String biz) {
        long currentTime = System.currentTimeMillis();
        String key = biz + "_key_" +currentTime;
        String value = biz + "_value_" + currentTime;
        return Pair.of(key, value);
    }
    
}
