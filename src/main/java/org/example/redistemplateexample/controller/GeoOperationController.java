package org.example.redistemplateexample.controller;

import org.example.redistemplateexample.redis.GeoOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import java.util.ArrayList;

@Tag(name = "GeoOperationController")
@RestController
@RequestMapping("/geo_operation")
public class GeoOperationController {

    @Autowired
    private GeoOperation geoOperation;

    @Operation(summary = "Add geospatial data to a key")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "longitude", description = "Longitude"),
            @Parameter(name = "latitude", description = "Latitude"),
            @Parameter(name = "member", description = "Member")
    })
    @PostMapping("/add")
    public long add(String key, double longitude, double latitude, String member) {

        Point point = new Point(longitude, latitude);
        return geoOperation.Add(key, point, member);
    }

    @Operation(summary = "Get the distance between two members")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "member1", description = "Member 1"),
            @Parameter(name = "member2", description = "Member 2")
    })
    @GetMapping("/distance")
    public String distance(String key, String member1, String member2) {
        return geoOperation.Dist(key, member1, member2).toString();
    }

    @Operation(summary = "Get the geohash of a member")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "members", description = "Members")
    })
    @GetMapping("/geohash")
    public String geohash(String key, String members) {
        ArrayList<String> membersList = new ArrayList<String>(Arrays.asList(members.split(",")));
        return geoOperation.Hash(key, membersList.toArray(new String[membersList.size()])).toString();
    }

    @Operation(summary = "Get the geospatial data of a member")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "member", description = "Member")
    })
    @PostMapping("/position")
    public String position(String key, String member) {
        return geoOperation.Pos(key, member).toString();
    }

    @Operation(summary = "Get the members within a certain radius of a member")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "member", description = "Member"),
            @Parameter(name = "radius", description = "Radius")
    })
    @GetMapping("/radius")
    public String radius(String key, String member, double radius) {
        return geoOperation.Radius(key, member, radius).toString();
    }

    @Operation(summary = "Get the members within a certain radius of a member with distance")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "member", description = "Member"),
            @Parameter(name = "radius", description = "Radius")
    })
    @GetMapping("/radius_with_distance")
    public String radiusWithDistance(String key, String member, double radius) {
        return geoOperation.Radius(key, member, new Distance(radius)).toString();
    }

    @Operation(summary = "Get the members within a certain radius of a member with distance and limit")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "member", description = "Member"),
            @Parameter(name = "radius", description = "Radius"),
            @Parameter(name = "limit", description = "Limit")
    })
    @GetMapping("/radius_with_distance_and_limit")
    public String radiusWithDistanceAndLimit(String key, String member, double radius, long limit) {
        return geoOperation
                .Radius(key, member, new Distance(radius), GeoRadiusCommandArgs.newGeoRadiusArgs().limit(limit))
                .toString();
    }

}
