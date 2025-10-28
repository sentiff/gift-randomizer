package org.sentiff.gift.randomizer.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.val;
import org.sentiff.gift.randomizer.commons.model.exceptions.ParticipantException;
import org.sentiff.gift.randomizer.commons.storage.Storage;
import org.sentiff.gift.randomizer.commons.utils.JsonUtils;
import org.sentiff.gift.randomizer.utils.ContentType;
import org.sentiff.gift.randomizer.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ParticipantController {

    private final Logger log = LoggerFactory.getLogger(ParticipantController.class);

    private final JsonUtils jsonUtils;
    private final ResponseUtils responseUtils;
    private final Storage memoryDB;

    private static final String UNKNOWN_ERROR = "UNKNOWN ERROR";

    @GetMapping("/getParticipants")
    public ResponseEntity<String> getParticipants() {
        log.info("queried /getParticipants endpoint");
        try {
            val participants = memoryDB.getParticipants();
            return responseUtils.createResponse(
                    jsonUtils.toJson(participants),
                    ContentType.APPLICATION_JSON.value,
                    HttpStatus.OK
            );
        } catch (JsonProcessingException e) {
            return responseUtils.createResponse(
                    e.getMessage(),
                    ContentType.TEXT_PLAIN.value,
                    HttpStatus.BAD_GATEWAY
            );
        } catch (Exception e) {
            return responseUtils.createResponse(
                    UNKNOWN_ERROR,
                    ContentType.TEXT_PLAIN.value,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/getParticipantByName")
    public ResponseEntity<String> getParticipantByName(@RequestParam(value = "name") String name) {
        log.info("queried /getParticipantByName endpoint");
        log.debug("processing request to get participant: {}", name);
        try {
            val participant = memoryDB.getParticipant(name);
            return responseUtils.createResponse(
                    jsonUtils.toJson(participant),
                    ContentType.APPLICATION_JSON.value,
                    HttpStatus.OK
            );
        } catch (ParticipantException | JsonProcessingException e) {
            return responseUtils.createResponse(
                    e.getMessage(),
                    ContentType.TEXT_PLAIN.value,
                    HttpStatus.BAD_GATEWAY
            );
        } catch (Exception e) {
            return responseUtils.createResponse(
                    UNKNOWN_ERROR,
                    ContentType.TEXT_PLAIN.value,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/getParticipantById")
    public ResponseEntity<String> getParticipantById(@RequestParam(value = "id", defaultValue = "1") Long id) {
        log.info("queried /getParticipantById endpoint");
        log.debug("processing request to get participant: {}", id);
        try {
            val participant = memoryDB.getParticipant(id);
            return responseUtils.createResponse(
                    jsonUtils.toJson(participant),
                    ContentType.APPLICATION_JSON.value,
                    HttpStatus.OK
            );
        } catch (ParticipantException | JsonProcessingException e) {
            return responseUtils.createResponse(
                    e.getMessage(),
                    ContentType.TEXT_PLAIN.value,
                    HttpStatus.BAD_GATEWAY
            );
        } catch (Exception e) {
            return responseUtils.createResponse(
                    UNKNOWN_ERROR,
                    ContentType.TEXT_PLAIN.value,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/addParticipant")
    public ResponseEntity<String> addParticipant(@RequestParam(value = "name") String name, @RequestParam(value = "giftIdeas") List<String> rawGiftIdeas) {
        log.info("queried /addParticipant endpoint");
        log.debug("processing request to add participant with name: {} and rawGiftIdeas: {}", name, rawGiftIdeas);
        try {
            val response = memoryDB.addParticipant(name, rawGiftIdeas);
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

    @PutMapping("/updateParticipantById")
    public ResponseEntity<String> updateParticipantById(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name, @RequestParam(value = "gift ideas") List<String> rawGiftIdeas) {
        log.info("queried /updateParticipantById endpoint");
        log.debug("processing request to update participant: {}, with name: {} and rawGiftIdeas: {}", id, name, rawGiftIdeas);
        try {
            val response = memoryDB.updateParticipant(id, name, rawGiftIdeas);
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

    @PatchMapping("/updateParticipantNameById")
    public ResponseEntity<String> updateParticipantById(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name) {
        log.info("queried /updateParticipantNameById endpoint");
        log.debug("processing request to update participant: {}, with name: {}", id, name);
        try {
            val response = memoryDB.updateParticipant(id, name);
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

    @PatchMapping("/updateParticipantGiftIdeasById")
    public ResponseEntity<String> updateParticipantById(@RequestParam(value = "id") Long id, @RequestParam(value = "gift ideas") List<String> rawGiftIdeas) {
        log.info("queried /updateParticipantGiftIdeasById endpoint");
        log.debug("processing request to update participant: {}, with rawGiftIdeas: {}", id, rawGiftIdeas);
        try {
            val response = memoryDB.updateParticipant(id, rawGiftIdeas);
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

    @DeleteMapping("/removeParticipantById")
    public ResponseEntity<String> removeParticipantById(@RequestParam(value = "id") Long id) {
        log.info("queried /removeParticipantById endpoint");
        log.debug("processing request to remove participant: {}", id);
        try {
            val response = memoryDB.removeParticipant(id);
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
