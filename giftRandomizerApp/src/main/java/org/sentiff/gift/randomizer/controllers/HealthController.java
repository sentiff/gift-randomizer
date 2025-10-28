package org.sentiff.gift.randomizer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final Logger log = LoggerFactory.getLogger(HealthController.class);

    @GetMapping("/health")
    public String getHealthStatus() {
        log.info("queried /health endpoint");
        return "OK";
    }
}
