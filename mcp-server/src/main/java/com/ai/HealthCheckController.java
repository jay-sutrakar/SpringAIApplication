package com.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    private static final Logger log = LoggerFactory.getLogger(HealthCheckController.class);

    @GetMapping("/")
    public String health() {
        log.info("Health check info retured.");
        return "OK";
    }
}
