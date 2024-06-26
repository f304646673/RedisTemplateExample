package org.example.redistemplateexample.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.redistemplateexample.redis.KeyOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "KeyOperationController")
@RestController
@RequestMapping("/key_operation")
public class KeyOperationController {

    @Autowired
    private KeyOperation keyOperation;

    @GetMapping("/exists")
    public boolean exists(@RequestParam(value = "key") String
            key) throws Exception {
        return keyOperation.Exists(key);
    }
}
