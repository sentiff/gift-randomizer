package org.sentiff.gift.randomizer.controllers;

import lombok.AllArgsConstructor;
import lombok.val;
import org.sentiff.gift.randomizer.commons.storage.Storage;
import org.sentiff.gift.randomizer.commons.utils.JsonUtils;
import org.sentiff.gift.randomizer.utils.ContentType;
import org.sentiff.gift.randomizer.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class GiftRandomizerController {

    private JsonUtils jsonUtils;
    private ResponseUtils responseUtils;
    private Storage memoryDB;

    @PostMapping("/createObservations")
    public ResponseEntity<String> createObservations(@RequestParam(value = "fairnessEnabled") Boolean areObservationsFair) {
        try {
            val response = memoryDB.createObservations(false);
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
            val response = memoryDB.createObservations(true);
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
            val observations = memoryDB.getObservations();
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
            val response = memoryDB.removeObservations();
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
            val response = memoryDB.getObservation(id);
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
            val response = memoryDB.getObservation(name);
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
