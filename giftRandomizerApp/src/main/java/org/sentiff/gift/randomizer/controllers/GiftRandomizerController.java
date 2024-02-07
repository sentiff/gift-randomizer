package org.sentiff.gift.randomizer.controllers;

import lombok.val;
import org.sentiff.gift.randomizer.commons.db.InMemoryDB;
import org.sentiff.gift.randomizer.utils.ContentType;
import org.sentiff.gift.randomizer.utils.JsonUtils;
import org.sentiff.gift.randomizer.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GiftRandomizerController {

    @Autowired
    private JsonUtils jsonUtils;

    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private InMemoryDB inMemoryDB;

    @PostMapping("/createObservations")
    public ResponseEntity<String> createObservations(@RequestParam(value = "fairnessEnabled") Boolean areObservationsFair) {
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

    @DeleteMapping("/removeObservations")
    public ResponseEntity<String> removeObservations() {
        try {
            val response = inMemoryDB.removeObservations();
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

    @GetMapping("/getObservationById")
    public ResponseEntity<String> getObservationById(@RequestParam(value = "id", defaultValue = "1") Long id) {
        try {
            val response = inMemoryDB.getObservationById(id);
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

    @GetMapping("/getObservationByName")
    public ResponseEntity<String> getObservationByName(@RequestParam(value = "Name") String name) {
        try {
            val response = inMemoryDB.getObservationByName(name);
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
}
