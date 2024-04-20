package org.example.redistemplateexample.controller;

import org.example.redistemplateexample.redis.SetOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

@Tag(name = "SetOperationController")
@RestController
@RequestMapping("/set_operation")
public class SetOperationController {

    @Autowired
    private SetOperation setOperation;

    @Operation(summary = "Add members to a set")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "values", description = "Values. Multiple values should be separated by commas. Example: \"1\", \"2\", \"3\"")
    })
    @PostMapping("/sadd")
    public Long sadd(String key, String values) {
        Long result = null;
        ArrayList<String> valuesList = new ArrayList<String>(Arrays.asList(values.split(",")));
        try {
            result = setOperation.SAdd(key, valuesList.toArray(new String[valuesList.size()]));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Get the number of elements in a set")
    @Parameters({
        @Parameter(name = "key", description = "Key")
    })
    @GetMapping("/scard")
    public Long scard(String key) {
        Long result = null;
        try {
            result = setOperation.SCard(key);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Get the difference between two sets")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "otherKey", description = "Other key")
    })
    @GetMapping("/sdiff")
    public String sdiff(String key, String otherKey) {
        String result = null;
        try {
            result = setOperation.SDiff(key, otherKey).toString();
        }
        catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }

    @Operation(summary = "Store the difference between two sets")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "otherKey", description = "Other key"),
        @Parameter(name = "destKey", description = "Destination key")
    })
    @PostMapping("/sdiffstore")
    public Long sdiffstore(String key, String otherKey, String destKey) {
        Long result = null;
        try {
            result = setOperation.SDiffStore(key, otherKey, destKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Get the intersection of two sets")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "otherKey", description = "Other key")
    })
    @GetMapping("/sinter")
    public String sinter(String key, String otherKey) {
        String result = null;
        try {
            result = setOperation.SInter(key, otherKey).toString();
        }
        catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }

    @Operation(summary = "Store the intersection of two sets")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "otherKey", description = "Other key"),
        @Parameter(name = "destKey", description = "Destination key")
    })
    @PostMapping("/sinterstore")
    public Long sinterstore(String key, String otherKey, String destKey) {
        Long result = null;
        try {
            result = setOperation.SInterStore(key, otherKey, destKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Check if a member exists in a set")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "value", description = "Value")
    })
    @GetMapping("/sismember")
    public boolean sismember(String key, String value) {
        boolean result = false;
        try {
            result = setOperation.SIsMember(key, value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Retrieve all members of a set")
    @Parameters({
        @Parameter(name = "key", description = "Key")
    })
    @GetMapping("/smembers")
    public String smembers(String key) {
        String result = null;
        try {
            result = setOperation.SMembers(key).toString();
        }
        catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }

    @Operation(summary = "Move a member from one set to another")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "otherKey", description = "Other key"),
        @Parameter(name = "value", description = "Value")
    })
    @PostMapping("/smove")
    public boolean smove(String key, String otherKey, String value) {
        boolean result = false;
        try {
            result = setOperation.SMove(key, value, otherKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Pop a random member from a set")
    @Parameters({
        @Parameter(name = "key", description = "Key")
    })
    @GetMapping("/spop")
    public String spop(String key) {
        String result = null;
        try {
            result = setOperation.SPop(key);
        }
        catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }

    @Operation(summary = "Return a random member from a set")
    @Parameters({
        @Parameter(name = "key", description = "Key")
    })
    @GetMapping("/srandmember")
    public String srandmember(String key) {
        String result = null;
        try {
            result = setOperation.SRandMember(key);
        }
        catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }

    @Operation(summary = "Retrieves a random sample of members from a set")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "count", description = "Count")
    })
    @GetMapping("/srandmembers")
    public List<String> srandmembers(String key, long count) {
        List<String> result = null;
        try {
            result = setOperation.SRandMember(key, count);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Remove members from a set")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "values", description = "Values")
    })
    @PostMapping("/srem")
    public Long srem(String key, String... values) {
        Long result = null;
        try {
            result = setOperation.SRem(key, values);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Operation(summary = "Get the union of two sets")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "otherKey", description = "Other key")
    })
    @GetMapping("/sunion")
    public String sunion(String key, String otherKey) {
        String result = null;
        try {
            result = setOperation.SUnion(key, otherKey).toString();
        }
        catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }

    @Operation(summary = "Store the union of two sets")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "otherKey", description = "Other key"),
        @Parameter(name = "destKey", description = "Destination key")
    })
    @PostMapping("/sunionstore")
    public Long sunionstore(String key, String otherKey, String destKey) {
        Long result = null;
        try {
            result = setOperation.SUnionStore(key, otherKey, destKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    
}
