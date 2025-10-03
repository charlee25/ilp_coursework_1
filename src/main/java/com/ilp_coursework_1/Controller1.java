package com.ilp_coursework_1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class Controller1 {
    @GetMapping("/isAlive")
    public String isAlive() {
        return "Service is alive";
    }
    @GetMapping("/api/v1/uid")
    public String getUid() {
        return "s2479712"; // Replace with your actual UID
    }
}
