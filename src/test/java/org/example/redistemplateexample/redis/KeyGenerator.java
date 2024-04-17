package org.example.redistemplateexample.redis;

import org.springframework.data.util.Pair;

import java.util.ArrayList;

public class KeyGenerator {
    public static Pair<String, String> generateString(String biz) {
        long currentTime = System.currentTimeMillis();
        String key = STR."\{biz}_key_\{currentTime}";
        String value = Long.toString(currentTime);
        return Pair.of(key, value);
    }

    public static Pair<String, Long> generateLong(String biz) {
        long currentTime = System.currentTimeMillis();
        String key = STR."\{biz}_key_\{currentTime}";
        return Pair.of(key, currentTime);
    }

    public static <T> Pair<String, ArrayList<Pair<String, String>>> generateHashString(String biz, int fieldCount) {
        long currentTime = System.currentTimeMillis();
        String key = STR."\{biz}_key_\{currentTime}";
        ArrayList<Pair<String, String>> list = new ArrayList<>();
        for (int i = 0; i < fieldCount; i++) {
            list.add(Pair.of(key + STR."field_\{i}", STR."value_\{i}"));
        }
        return Pair.of(key, list);
    }

    public static <T> Pair<String, ArrayList<Pair<String, Long>>> generateHashLong(String biz, int fieldCount) {
        long currentTime = System.currentTimeMillis();
        String key = STR."\{biz}_key_\{currentTime}";
        ArrayList<Pair<String, Long>> list = new ArrayList<>();
        for (int i = 0; i < fieldCount; i++) {
            list.add(Pair.of(key + STR."field_\{i}", currentTime + i));
        }
        return Pair.of(key, list);
    }
    
}
