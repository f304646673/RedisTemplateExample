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
        Long result = null;
        try {
            result = listOperation.LPush(key, value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Push a value to the end of a list")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "value", description = "Value")
    })
    @PostMapping("/rpush")
    public Long rpush(String key, String value) {
        Long result = null;
        try {
            result = listOperation.RPush(key, value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Get the number of elements in a list")
    @Parameters({
        @Parameter(name = "key", description = "Key")
    })
    @GetMapping("/llen")
    public Long llen(String key) {
        Long result = null;
        try {
            result = listOperation.LLen(key);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Get the element at the specified index in a list")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "index", description = "Index")
    })
    @GetMapping("/lindex")
    public String lindex(String key, long index) {
        String result = null;
        try {
            result = listOperation.LIndex(key, index);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Insert an element before or after another element in a list")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "pivot", description = "Pivot"),
        @Parameter(name = "value", description = "Value"),
        @Parameter(name = "before", description = "Before or after")
    })
    @PutMapping("/linsert")
    public Long linsert(String key,  String pivot, String value, boolean before) {
        Long result = null;
        try {
            result = listOperation.LInsert(key, pivot, value, before);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Remove and return the first element in a list")
    @Parameters({
        @Parameter(name = "key", description = "Key")
    })
    @DeleteMapping("/lpop")
    public String lpop(String key) {
        String result = null;
        try {
            result = listOperation.LPop(key);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Remove and return the last element in a list")
    @Parameters({
        @Parameter(name = "key", description = "Key")
    })
    @DeleteMapping("/rpop")
    public String rpop(String key) {
        String result = null;
        try {
            result = listOperation.RPop(key);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Remove the first occurrence of a value from a list")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "count", description = "Count"),
        @Parameter(name = "value", description = "Value")
    })
    @DeleteMapping("/lrem")
    public Long lrem(String key, long count, String value) {
        Long result = null;
        try {
            result = listOperation.LRem(key, count, value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Set the value of an element in a list by its index")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "index", description = "Index"),
        @Parameter(name = "value", description = "Value")
    })
    @PutMapping("/lset")
    public boolean  lset(String key, long index, String value) {
        try {
            listOperation.LSet(key, index, value);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Operation(summary = "Trim a list to the specified range")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "start", description = "Start"),
        @Parameter(name = "end", description = "End")
    })
    @PutMapping("/ltrim")
    public boolean ltrim(String key, long start, long end) {
        try {
            listOperation.LTrim(key, start, end);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Operation(summary = "Remove and return the last element in a list, or block until one is available")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "count", description = "count")
    })
    @DeleteMapping("/brpop")
    public List<String> brpop(String key, long count) {
        List<String> result = null;
        try {
            result = listOperation.BRPop(key, count);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Remove and return the first element in a list, or block until one is available")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "count", description = "count")
    })
    @DeleteMapping("/blpop")
    public List<String> blpop(String key, long count) {
        List<String> result = null;
        try {
            result = listOperation.BLPop(key, count);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Remove the last element in a list and append it to another list")
    @Parameters({
        @Parameter(name = "source", description = "Source"),
        @Parameter(name = "destination", description = "Destination")
    })
    @DeleteMapping("/rpoplpush")
    public String rpoplpush(String source, String destination) {
        String result = null;
        try {
            result = listOperation.RPopLPush(source, destination);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
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
        String result = null;
        try {
            result = listOperation.BRPopLPush(source, destination, timeout, unit);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Pushes a value to the left of a list, only if the key already exists.")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "value", description = "Value")
    })
    @PostMapping("/lpushx")
    public Long lpushx(String key, String value) {
        Long result = null;
        try {
            result = listOperation.LPushX(key, value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Retrieves a range of elements from a list stored at the specified key.")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "start", description = "Start"),
        @Parameter(name = "end", description = "End")
    })
    @GetMapping("/lrange")
    public List<String> lrange(String key, long start, long end) {
        List<String> result = null;
        try {
            result = listOperation.LRange(key, start, end);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Pushes a value to the right of a list, only if the key already exists.")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "value", description = "Value")
    })  
    @PostMapping("/rpushx")
    public Long rpushx(String key, String value) {
        Long result = null;
        try {
            result = listOperation.RPushX(key, value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
