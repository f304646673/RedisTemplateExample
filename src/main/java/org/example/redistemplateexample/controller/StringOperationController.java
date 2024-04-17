package org.example.redistemplateexample.controller;

import org.example.redistemplateexample.redis.StringOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/string_operation")
public class StringOperationController {
    
    @Autowired
    private StringOperation stringOperation;

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
