package org.example.redistemplateexample.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.redistemplateexample.redis.StringOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "StringOperationController")
@RestController
@RequestMapping("/string_operation")
public class StringOperationController {
    
    @Autowired
    private StringOperation stringOperation;

    @Operation(summary = "Set key-value pair")
    @Parameters({
        @Parameter(name = "key", description = "Key"),
        @Parameter(name = "value", description = "Value")
    })
    @PostMapping("/set")
    public boolean set(@RequestParam(value = "key") String
            key, @RequestParam(value = "value") String value) {
        try {
            return stringOperation.Set(key, value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Operation(summary = "Get value by key")
    @Parameters({
        @Parameter(name = "key", description = "Key")
    })
    @GetMapping("/get")
    public String get(@RequestParam(value = "key") String key) {
        String result = null;
        try {
            result = stringOperation.Get(key);
        }
        catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }   
}
