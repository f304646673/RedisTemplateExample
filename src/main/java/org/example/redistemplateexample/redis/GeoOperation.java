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
    
    /**
     * Adds a member with a geospatial point to a sorted set stored at the specified key.
     *
     * @param key the key of the sorted set
     * @param point the geospatial point to add
     * @param member the member to add
     * @return the number of elements added to the sorted set
     * @throws Exception if an error occurs while adding the member
     */
    public Long Add(String key, Point point, String member) throws Exception {
        return redisTemplate.opsForGeo().add(key, point, member);
    }

    /**
     * Adds a geospatial location to a sorted set stored at the specified key.
     *
     * @param key the key of the sorted set
     * @param location the geospatial location to add
     * @return the number of elements added to the sorted set
     * @throws Exception if an error occurs while adding the location
     */
    public Long Add(String key, RedisGeoCommands.GeoLocation<String> location) throws Exception {
        return redisTemplate.opsForGeo().add(key, location);
    }

    /**
     * Adds member-coordinate pairs to a geo set in Redis.
     *
     * @param key                 the key of the geo set
     * @param memberCoordinateMap a map containing member-coordinate pairs
     * @return the number of members added to the geo set
     * @throws Exception if an error occurs while adding members to the geo set
     */
    public Long Add(String key, Map<String, Point> memberCoordinateMap) throws Exception {
        return redisTemplate.opsForGeo().add(key, memberCoordinateMap);
    }

    /**
     * Calculates the distance between two members in a geospatial index stored at the specified key.
     *
     * @param key the key of the geospatial index
     * @param member1 the first member
     * @param member2 the second member
     * @return the distance between the two members
     * @throws Exception if an error occurs while calculating the distance
     */
    public Distance Dist(String key, String member1, String member2) throws Exception {
        return redisTemplate.opsForGeo().distance(key, member1, member2);
    }

    /**
     * Calculates the distance between two members in a geospatial index stored at the specified key.
     *
     * @param key the key of the geospatial index
     * @param member1 the first member
     * @param member2 the second member
     * @param metric the metric to use for distance calculation (e.g., {@link Metric#KILOMETERS}, {@link Metric#MILES})
     * @return the distance between the two members
     * @throws Exception if an error occurs while calculating the distance
     */
    public Distance Dist(String key, String member1, String member2, Metric metric) throws Exception {
        return redisTemplate.opsForGeo().distance(key, member1, member2, metric);
    }

    /**
     * Retrieves the geohash strings representing the specified members in the given key.
     *
     * @param key     the key to retrieve the geohash strings from
     * @param members the members for which to retrieve the geohash strings
     * @return a list of geohash strings representing the specified members
     * @throws Exception if an error occurs while retrieving the geohash strings
     */
    public List<String> Hash(String key, String... members) throws Exception {
        return redisTemplate.opsForGeo().hash(key, members);
    }

    /**
     * Retrieves the position (longitude and latitude) of a member in a geospatial index stored at the specified key.
     *
     * @param key the key of the geospatial index
     * @param member the member whose position needs to be retrieved
     * @return a list of Point objects representing the longitude and latitude of the member
     * @throws Exception if an error occurs while retrieving the position
     */
    public java.util.List<Point> Pos(String key, String member) throws Exception {
        return redisTemplate.opsForGeo().position(key, member);
    }

    /**
     * Returns the geospatial members that are within the given radius of the specified key.
     *
     * @param key    the key to perform the radius operation on
     * @param within the circle representing the radius and center point
     * @return a {@link GeoResults} containing the geospatial members within the radius
     * @throws Exception if an error occurs during the operation
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> Radius(String key, Circle within) throws Exception {
        return redisTemplate.opsForGeo().radius(key, within);
    }

    /**
     * Returns the geospatial members that are within the given radius of the specified key and circle.
     *
     * @param key the key representing the geospatial index
     * @param within the circle representing the radius within which to search for members
     * @param args additional arguments for the geo radius command
     * @return a {@link GeoResults} object containing the geospatial members within the specified radius
     * @throws Exception if an error occurs while executing the command
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> Radius(String key, Circle within, RedisGeoCommands.GeoRadiusCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().radius(key, within, args);
    }

    /**
     * Retrieves all the members within the specified radius from the given key and member.
     *
     * @param key    the key to search within
     * @param member the member to search around
     * @param radius the radius within which to search for members
     * @return a GeoResults object containing the members found within the specified radius
     * @throws Exception if an error occurs while performing the operation
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> Radius(String key, String member, double radius) throws Exception {
        return redisTemplate.opsForGeo().radius(key, member, radius);
    }

    /**
     * Returns the geospatial members that are within the specified distance from the given member in the sorted set stored at the specified key.
     *
     * @param key the key of the sorted set
     * @param member the member to calculate the distance from
     * @param distance the maximum distance from the member
     * @return a {@link GeoResults} containing the geospatial members and their distances
     * @throws Exception if an error occurs while performing the operation
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> Radius(String key, String member, Distance distance) throws Exception {
        return redisTemplate.opsForGeo().radius(key, member, distance);
    }

    /**
     * Returns the geospatial members that are within the given distance from the specified member.
     *
     * @param key the key of the geospatial index
     * @param member the member to calculate the distance from
     * @param distance the maximum distance from the member
     * @param args additional arguments for the geo radius command
     * @return a list of geospatial members within the specified distance
     * @throws Exception if an error occurs while executing the command
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> Radius(String key, String member, Distance distance, RedisGeoCommands.GeoRadiusCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().radius(key, member, distance, args);
    }

    /**
     * Removes one or more members from a sorted set stored at the specified key.
     *
     * @param key     the key of the sorted set
     * @param members the members to be removed
     * @return the number of members removed from the sorted set
     * @throws Exception if an error occurs while removing the members
     */
    public Long Remove(String key, String... members) throws Exception {
        return redisTemplate.opsForGeo().remove(key, members);
    }

    /**
     * Searches for geo locations within a given circle in Redis.
     *
     * @param key    the key of the geo set
     * @param within the circle within which to search for geo locations
     * @return a {@link GeoResults} containing the geo locations found within the circle
     * @throws Exception if an error occurs during the search operation
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> Search(String key, Circle within) throws Exception {
        return redisTemplate.opsForGeo().search(key, within);
    }

    /**
     * Searches for geo locations within a specified radius from a given reference point.
     *
     * @param key the key of the geo set
     * @param reference the reference point for the search
     * @param radius the maximum distance from the reference point to search within
     * @return a list of geo locations within the specified radius
     * @throws Exception if an error occurs during the search operation
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> Search(String key, GeoReference<String> reference, Distance radius) throws Exception {
        return redisTemplate.opsForGeo().search(key, reference, radius);
    }

    /**
     * Searches for geo locations within a specified radius from a given reference point.
     *
     * @param key the key of the geo set
     * @param reference the reference point for the search
     * @param radius the maximum distance from the reference point to search within
     * @param args additional arguments for the geo radius command
     * @return a list of geo locations within the specified radius
     * @throws Exception if an error occurs during the search operation
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> Search(String key, GeoReference<String> reference, Distance radius, RedisGeoCommands.GeoRadiusCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().search(key, reference, radius, args);
    }

    /**
     * Searches for geo locations within the specified bounding box in Redis.
     *
     * @param key the key of the geo set in Redis
     * @param reference the reference point for the search
     * @param boundingBox the bounding box to search within
     * @return a list of geo locations within the bounding box
     * @throws Exception if an error occurs during the search operation
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> Search(String key, GeoReference<String> reference, BoundingBox boundingBox) throws Exception {
        return redisTemplate.opsForGeo().search(key, reference, boundingBox);
    }

    /**
     * Searches for geo locations within the specified bounding box and returns the results.
     *
     * @param key the key of the geo set
     * @param reference the reference point for the search
     * @param boundingBox the bounding box to search within
     * @param args additional arguments for the search
     * @return a {@link GeoResults} object containing the search results
     * @throws Exception if an error occurs during the search operation
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> Search(String key, GeoReference<String> reference, BoundingBox boundingBox, RedisGeoCommands.GeoRadiusCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().search(key, reference, boundingBox, args);
    }

    /**
     * Searches for geo locations within a given area.
     *
     * @param key the key of the geo set
     * @param reference the reference point for the search
     * @param geoPredicate the shape representing the search area
     * @param args additional arguments for the search command
     * @return a list of geo locations matching the search criteria
     * @throws Exception if an error occurs during the search operation
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> Search(String key, GeoReference<String> reference, GeoShape geoPredicate, RedisGeoCommands.GeoSearchCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().search(key, reference, geoPredicate, args);
    }

    /**
     * Searches for members within a given radius and stores the results in a destination key.
     *
     * @param key the key representing the geo set to search within
     * @param destKey the key representing the destination geo set to store the results
     * @param within the circle representing the search area
     * @return the number of members stored in the destination geo set
     * @throws Exception if an error occurs during the operation
     */
    public Long SearchAndStore(String key, String destKey, Circle within) throws Exception {
        return redisTemplate.opsForGeo().searchAndStore(key, destKey, within);
    }

    /**
     * Searches for geo coordinates within a specified radius and stores the results in a destination key.
     *
     * @param key the key to search for geo coordinates
     * @param destKey the key to store the search results
     * @param reference the reference point for the search
     * @param radius the maximum distance from the reference point to search for geo coordinates
     * @return the number of geo coordinates found and stored in the destination key
     * @throws Exception if an error occurs during the search and store operation
     */
    public Long SearchAndStore(String key, String destKey, GeoReference<String> reference, Distance radius) throws Exception {
        return redisTemplate.opsForGeo().searchAndStore(key, destKey, reference, radius);
    }

    /**
     * Searches for geo coordinates within a specified radius and stores the results in a destination key.
     *
     * @param key the key representing the geo set to search
     * @param destKey the key representing the destination set to store the results
     * @param reference the reference point for the search
     * @param radius the maximum distance from the reference point to search within
     * @param args additional arguments for the search and store operation
     * @return the number of elements added to the destination set
     * @throws Exception if an error occurs during the search and store operation
     */
    public Long SearchAndStore(String key, String destKey, GeoReference<String> reference, Distance radius, RedisGeoCommands.GeoSearchStoreCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().searchAndStore(key, destKey, reference, radius, args);
    }

    /**
     * Searches for members within a specified bounding box in a sorted set stored at the given key,
     * and stores the matching members in a new sorted set at the destination key.
     *
     * @param key the key of the sorted set to search
     * @param destKey the key of the sorted set to store the matching members
     * @param reference the reference point for the search
     * @param boundingBox the bounding box to search within
     * @return the number of members stored in the destination sorted set
     * @throws Exception if an error occurs during the operation
     */
    public Long SearchAndStore(String key, String destKey, GeoReference<String> reference, BoundingBox boundingBox) throws Exception {
        return redisTemplate.opsForGeo().searchAndStore(key, destKey, reference, boundingBox);
    }

    /**
     * Searches for geo locations within a specified bounding box and stores the results in a destination key.
     * 
     * @param key the key representing the geo set to search within
     * @param destKey the key to store the search results
     * @param reference the reference point for the search
     * @param boundingBox the bounding box to search within
     * @param args additional arguments for the search and store operation
     * @return the number of elements added to the destination key
     * @throws Exception if an error occurs during the search and store operation
     */
    public Long SearchAndStore(String key, String destKey, GeoReference<String> reference, BoundingBox boundingBox, RedisGeoCommands.GeoSearchStoreCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().searchAndStore(key, destKey, reference, boundingBox, args);
    }

    /**
     * Searches for elements matching the given criteria and stores the results in a destination key.
     *
     * @param key the key to search in
     * @param destKey the key to store the results in
     * @param reference the reference point for the search
     * @param geoPredicate the shape representing the search criteria
     * @param args additional arguments for the search and store operation
     * @return the number of elements stored in the destination key
     * @throws Exception if an error occurs during the search and store operation
     */
    public Long SearchAndStore(String key, String destKey, GeoReference<String> reference, GeoShape geoPredicate, RedisGeoCommands.GeoSearchStoreCommandArgs args) throws Exception {
        return redisTemplate.opsForGeo().searchAndStore(key, destKey, reference, geoPredicate, args);
    }
}
