package com.dhiraj.miniurl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public HealthResponse health(){
        return new HealthResponse("UP","miniurl-backend");
    }

    record HealthResponse(String status,String service){}
}
