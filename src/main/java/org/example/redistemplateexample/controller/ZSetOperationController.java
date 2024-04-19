package org.example.redistemplateexample.controller;

import java.util.Set;

import org.example.redistemplateexample.redis.ZSetOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ZSetOperationController")
@RestController
@RequestMapping("/zset_operation")
public class ZSetOperationController {
    
    @Autowired
    private ZSetOperation zSetOperation;

    @Operation(summary = "Add member to sorted set")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "value", description = "Value"),
        @Parameter(name = "score", description = "Score")
    })
    @PostMapping("/zadd")
    public boolean add(@RequestParam(value = "key") String
            key, @RequestParam(value = "value") String value, @RequestParam(value = "score") double score) {
        try {
            return zSetOperation.ZAdd(key, value, score);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Operation(summary = "Get size of sorted set")
    @Parameters({
        @Parameter(name = "key", description = "Key")
    })
    @GetMapping("/zcard")
    public Long size(@RequestParam(value = "key") String key) {
        Long result = null;
        try {
            result = zSetOperation.ZCard(key);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Operation(summary = "Get size of sorted set by score range")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "min", description = "Min score"),
        @Parameter(name = "max", description = "Max score")
    })
    @GetMapping("/zcount")
    public Long count(@RequestParam(value = "key") String
            key, @RequestParam(value = "min") double min, @RequestParam(value = "max") double max) {
        Long result = null;
        try {
            result = zSetOperation.ZCount(key, min, max);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }   


    @Operation(summary = "Increment score of member")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "value", description = "Value"),
        @Parameter(name = "delta", description = "Increment")
    })
    @PostMapping("/zincrby")
    public Double incrBy(@RequestParam(value = "key") String
            key, @RequestParam(value = "value") String value, @RequestParam(value = "delta") double delta) {
        Double result = null;
        try {
            result = zSetOperation.ZIncrBy(key, value, delta);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Operation(summary = "Get members by index range")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "start", description = "Start index"),
        @Parameter(name = "end", description = "End index")
    })
    @GetMapping("/zrange")
    public Set<String> range(@RequestParam(value = "key") String
            key, @RequestParam(value = "start") long start, @RequestParam(value = "end") long end) {
        Set<String> result = null;
        try {
            result = zSetOperation.ZRange(key, start, end);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Operation(summary = "Get members by score range")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "min", description = "Min score"),
        @Parameter(name = "max", description = "Max score")
    })
    @GetMapping("/zrangebyscore")
    public Set<String> rangeByScore(@RequestParam(value = "key") String
            key, @RequestParam(value = "min") double min, @RequestParam(value = "max") double max) {
        Set<String> result = null;
        try {
            result = zSetOperation.ZRangeByScore(key, min, max);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }  


    @Operation(summary = "Get rank of member")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "value", description = "Value")
    })
    @GetMapping("/zrank")
    public Long rank(@RequestParam(value = "key") String
            key, @RequestParam(value = "value") String value) {
        Long result = null;
        try {
            result = zSetOperation.ZRank(key, value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Operation(summary = "Remove member from sorted set")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "value", description = "Value")
    })
    @DeleteMapping("/zrem")
    public Long remove(@RequestParam(value = "key") String
            key, @RequestParam(value = "value") String value) {
        try {
            return zSetOperation.ZRem(key, value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Operation(summary = "Remove members by index range")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "start", description = "Start index"),
        @Parameter(name = "end", description = "End index")
    })
    @DeleteMapping("/zremrangebyrank")
    public Long removeByRank(@RequestParam(value = "key") String
            key, @RequestParam(value = "start") long start, @RequestParam(value = "end") long end) {
        try {
            return zSetOperation.ZRemRange(key, start, end);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Operation(summary = "Remove members by score range")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "min", description = "Min score"),
        @Parameter(name = "max", description = "Max score")
    })
    @DeleteMapping("/zremrangebyscore")
    public Long removeByScore(@RequestParam(value = "key") String
            key, @RequestParam(value = "min") double min, @RequestParam(value = "max") double max) {
        try {
            return zSetOperation.ZRemRangeByScore(key, min, max);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Operation(summary = "Get score of member")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "value", description = "Value")
    })
    @GetMapping("/zscore")
    public Double score(@RequestParam(value = "key") String
            key, @RequestParam(value = "value") String value) {
        Double result = null;
        try {
            result = zSetOperation.ZScore(key, value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
