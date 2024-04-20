package org.example.redistemplateexample.controller;

import org.example.redistemplateexample.redis.ListOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Tag(name = "ListOperationController")
@RestController
@RequestMapping("/list_operation")
public class ListOperationController {

    @Autowired
    private ListOperation listOperation;

    @Operation(summary = "Add members to a list")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "value", description = "value")
    })
    @PostMapping("/lpush")
    public Long lpush(String key, String value) {
        return listOperation.LPush(key, value);
    }

    @Operation(summary = "Push a value to the end of a list")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "value", description = "Value")
    })
    @PostMapping("/rpush")
    public Long rpush(String key, String value) {
        return listOperation.RPush(key, value);
    }

    @Operation(summary = "Get the number of elements in a list")
    @Parameters({
            @Parameter(name = "key", description = "Key")
    })
    @GetMapping("/llen")
    public Long llen(String key) {
        return listOperation.LLen(key);
    }

    @Operation(summary = "Get the element at the specified index in a list")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "index", description = "Index")
    })
    @GetMapping("/lindex")
    public String lindex(String key, long index) {
        return listOperation.LIndex(key, index);
    }

    @Operation(summary = "Insert an element before or after another element in a list")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "pivot", description = "Pivot"),
            @Parameter(name = "value", description = "Value"),
            @Parameter(name = "before", description = "Before or after")
    })
    @PutMapping("/linsert")
    public Long linsert(String key, String pivot, String value, boolean before) {
        return listOperation.LInsert(key, pivot, value, before);
    }

    @Operation(summary = "Remove and return the first element in a list")
    @Parameters({
            @Parameter(name = "key", description = "Key")
    })
    @DeleteMapping("/lpop")
    public String lpop(String key) {
        return listOperation.LPop(key);
    }

    @Operation(summary = "Remove and return the last element in a list")
    @Parameters({
            @Parameter(name = "key", description = "Key")
    })
    @DeleteMapping("/rpop")
    public String rpop(String key) {
        return listOperation.RPop(key);
    }

    @Operation(summary = "Remove the first occurrence of a value from a list")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "count", description = "Count"),
            @Parameter(name = "value", description = "Value")
    })
    @DeleteMapping("/lrem")
    public Long lrem(String key, long count, String value) {
        return listOperation.LRem(key, count, value);
    }

    @Operation(summary = "Set the value of an element in a list by its index")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "index", description = "Index"),
            @Parameter(name = "value", description = "Value")
    })
    @PutMapping("/lset")
    public boolean lset(String key, long index, String value) {
        listOperation.LSet(key, index, value);
        return true;
    }

    @Operation(summary = "Trim a list to the specified range")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "start", description = "Start"),
            @Parameter(name = "end", description = "End")
    })
    @PutMapping("/ltrim")
    public boolean ltrim(String key, long start, long end) {
        listOperation.LTrim(key, start, end);
        return true;
    }

    @Operation(summary = "Remove and return the last element in a list, or block until one is available")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "count", description = "count")
    })
    @DeleteMapping("/brpop")
    public List<String> brpop(String key, long count) {
        return listOperation.BRPop(key, count);
    }

    @Operation(summary = "Remove and return the first element in a list, or block until one is available")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "count", description = "count")
    })
    @DeleteMapping("/blpop")
    public List<String> blpop(String key, long count) {
        return listOperation.BLPop(key, count);
    }

    @Operation(summary = "Remove the last element in a list and append it to another list")
    @Parameters({
            @Parameter(name = "source", description = "Source"),
            @Parameter(name = "destination", description = "Destination")
    })
    @DeleteMapping("/rpoplpush")
    public String rpoplpush(String source, String destination) {
        return listOperation.RPopLPush(source, destination);
    }

    @Operation(summary = "Remove the last element in a list and append it to another list, or block until one is available")
    @Parameters({
            @Parameter(name = "source", description = "Source"),
            @Parameter(name = "destination", description = "Destination"),
            @Parameter(name = "timeout", description = "Timeout"),
            @Parameter(name = "unit", description = "Unit")
    })
    @DeleteMapping("/brpoplpush")
    public String brpoplpush(String source, String destination, long timeout, TimeUnit unit) {
        return listOperation.BRPopLPush(source, destination, timeout, unit);
    }

    @Operation(summary = "Pushes a value to the left of a list, only if the key already exists.")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "value", description = "Value")
    })
    @PostMapping("/lpushx")
    public Long lpushx(String key, String value) {
        return listOperation.LPushX(key, value);
    }

    @Operation(summary = "Retrieves a range of elements from a list stored at the specified key.")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "start", description = "Start"),
            @Parameter(name = "end", description = "End")
    })
    @GetMapping("/lrange")
    public List<String> lrange(String key, long start, long end) {
        return listOperation.LRange(key, start, end);
    }

    @Operation(summary = "Pushes a value to the right of a list, only if the key already exists.")
    @Parameters({
            @Parameter(name = "key", description = "Key"),
            @Parameter(name = "value", description = "Value")
    })
    @PostMapping("/rpushx")
    public Long rpushx(String key, String value) {
        return listOperation.RPushX(key, value);
    }

}
