package com.tanzu.rewrite.demo.ips_are_hard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class ApiController {
    @GetMapping("echo")
    public String echo(@RequestParam String message) {
        return String.format("message: %s", message);
    }

    @GetMapping("ping")
    public String ping() {
        return "pong";
    }
}
