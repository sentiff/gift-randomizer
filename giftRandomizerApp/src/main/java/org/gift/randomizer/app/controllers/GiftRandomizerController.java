package org.gift.randomizer.app.controllers;

import lombok.val;
import org.gift.randomizer.app.db.InMemoryDB;
import org.gift.randomizer.app.utils.ContentType;
import org.gift.randomizer.app.utils.JsonUtils;
import org.gift.randomizer.app.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GiftRandomizerController {

    @Autowired
    private JsonUtils jsonUtils;

    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private InMemoryDB inMemoryDB;

    @PostMapping("/createObservations")
    public ResponseEntity<String> createObservations() {
        try {
            val response = inMemoryDB.createObservations(false);
            return responseUtils.createResponse(
                    jsonUtils.toJson(response),
                    ContentType.APPLICATION_JSON.value,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return responseUtils.createResponse(
                    e.getMessage(),
                    ContentType.TEXT_PLAIN.value,
                    HttpStatus.BAD_GATEWAY
            );
        }
    }

    @PostMapping("/recreateObservations")
    public ResponseEntity<String> recreateObservations() {
        try {
            val response = inMemoryDB.createObservations(true);
            return responseUtils.createResponse(
                    jsonUtils.toJson(response),
                    ContentType.APPLICATION_JSON.value,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return responseUtils.createResponse(
                    e.getMessage(),
                    ContentType.TEXT_PLAIN.value,
                    HttpStatus.BAD_GATEWAY
            );
        }
    }

    @GetMapping("/getObservations")
    public ResponseEntity<String> getObservations() {
        try {
            val observations = inMemoryDB.getObservations();
            return responseUtils.createResponse(
                    jsonUtils.toJson(observations),
                    ContentType.APPLICATION_JSON.value,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return responseUtils.createResponse(
                    e.getMessage(),
                    ContentType.TEXT_PLAIN.value,
                    HttpStatus.BAD_GATEWAY
            );
        }
    }


}
