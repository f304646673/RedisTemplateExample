package org.example.redistemplateexample.redis;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.BoundingBox;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.data.redis.domain.geo.GeoShape;
import org.springframework.stereotype.Component;
import org.springframework.data.geo.Metric;

import jakarta.annotation.Resource;

import java.util.List;
import java.util.Map;

@Component
public class GeoOperation {

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    
    public Long Add(String key, Point point, String member) throws Exception {
        return redisTemplate.opsForGeo().add(key, point, member);
    }

    public Long Add(String key, RedisGeoCommands.GeoLocation<String> location) throws Exception {
        return redisTemplate.opsForGeo().add(key, location);
    }

    public Long Add(String key, Map<String, Point> memberCoordinateMap) throws Exception {
        return redisTemplate.opsForGeo().add(key, memberCoordinateMap);
    }

    public Distance Dist(String key, String member1, String member2) throws Exception {
        return redisTemplate.opsForGeo().distance(key, member1, member2);
    }

    public Distance Dist(String key, String member1, String member2, Metric metric) throws Exception {
        return redisTemplate.opsForGeo().distance(key, member1, member2, metric);
    }

    public List<String> Hash(String key, String... members) throws Exception {
        return redisTemplate.opsForGeo().hash(key, members);
    }

    public java.util.List<Point> Pos(String key, String member) throws Exception {
        return redisTemplate.opsForGeo().position(key, member);
    }

    public GeoResults<RedisGeoCommands.GeoLocation<String>> Radius(String key, Circle within) throws Exception {
        return redisTemplate.opsForGeo().radius(key, within);
    }

    public GeoResults<RedisGeoCommands.GeoLocation<String>> Radius(String key, Circle within, RedisGeoCommands.GeoRadiusCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().radius(key, within, args);
    }

    public GeoResults<RedisGeoCommands.GeoLocation<String>> Radius(String key, String member, double radius) throws Exception {
        return redisTemplate.opsForGeo().radius(key, member, radius);
    }

    public GeoResults<RedisGeoCommands.GeoLocation<String>> Radius(String key, String member, Distance distance) throws Exception {
        return redisTemplate.opsForGeo().radius(key, member, distance);
    }

    public GeoResults<RedisGeoCommands.GeoLocation<String>> Radius(String key, String member, Distance distance, RedisGeoCommands.GeoRadiusCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().radius(key, member, distance, args);
    }

    public Long Remove(String key, String... members) throws Exception {
        return redisTemplate.opsForGeo().remove(key, members);
    }

    public GeoResults<RedisGeoCommands.GeoLocation<String>> Search(String key, Circle within) throws Exception {
        return redisTemplate.opsForGeo().search(key, within);
    }

    public GeoResults<RedisGeoCommands.GeoLocation<String>> Search(String key, GeoReference<String> reference, Distance radius) throws Exception {
        return redisTemplate.opsForGeo().search(key, reference, radius);
    }

    public GeoResults<RedisGeoCommands.GeoLocation<String>> Search(String key, GeoReference<String> reference, Distance radius, RedisGeoCommands.GeoRadiusCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().search(key, reference, radius, args);
    }

    public GeoResults<RedisGeoCommands.GeoLocation<String>> Search(String key, GeoReference<String> reference, BoundingBox boundingBox) throws Exception {
        return redisTemplate.opsForGeo().search(key, reference, boundingBox);
    }

    public GeoResults<RedisGeoCommands.GeoLocation<String>> Search(String key, GeoReference<String> reference, BoundingBox boundingBox, RedisGeoCommands.GeoRadiusCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().search(key, reference, boundingBox, args);
    }

    public GeoResults<RedisGeoCommands.GeoLocation<String>> Search(String key, GeoReference<String> reference, GeoShape geoPredicate, RedisGeoCommands.GeoSearchCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().search(key, reference, geoPredicate, args);
    }

    public Long SearchAndStore(String key, String destKey, Circle within) throws Exception {
        return redisTemplate.opsForGeo().searchAndStore(key, destKey, within);
    }

    public Long SearchAndStore(String key, String destKey, GeoReference<String> reference, Distance radius) throws Exception {
        return redisTemplate.opsForGeo().searchAndStore(key, destKey, reference, radius);
    }

    public Long SearchAndStore(String key, String destKey, GeoReference<String> reference, Distance radius, RedisGeoCommands.GeoSearchStoreCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().searchAndStore(key, destKey, reference, radius, args);
    }

    public Long SearchAndStore(String key, String destKey, GeoReference<String> reference, BoundingBox boundingBox) throws Exception {
        return redisTemplate.opsForGeo().searchAndStore(key, destKey, reference, boundingBox);
    }

    public Long SearchAndStore(String key, String destKey, GeoReference<String> reference, BoundingBox boundingBox, RedisGeoCommands.GeoSearchStoreCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().searchAndStore(key, destKey, reference, boundingBox, args);
    }

    public Long SearchAndStore(String key, String destKey, GeoReference<String> reference, GeoShape geoPredicate, RedisGeoCommands.GeoSearchStoreCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().searchAndStore(key, destKey, reference, geoPredicate, args);
    }
}
