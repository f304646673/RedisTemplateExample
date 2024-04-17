package org.example.redistemplateexample.redis;

import org.springframework.data.util.Pair;

public class KeyGenerator {
    public static Pair<String, String> generateString(String biz) {
        long currentTime = System.currentTimeMillis();
        String key = biz + "_key_" +currentTime;
        String value = Long.toString(currentTime);
        return Pair.of(key, value);
    }

    public static Pair<String, Long> generateLong(String biz) {
        long currentTime = System.currentTimeMillis();
        String key = biz + "_key_" +currentTime;
        return Pair.of(key, currentTime);
    }
    
}
