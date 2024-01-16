package org.sentiff.gift.randomizer.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.val;
import org.sentiff.gift.randomizer.db.model.exceptions.ParticipantException;
import org.sentiff.gift.randomizer.db.InMemoryDB;
import org.sentiff.gift.randomizer.utils.ContentType;
import org.sentiff.gift.randomizer.utils.JsonUtils;
import org.sentiff.gift.randomizer.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParticipantController {

    @Autowired
    private JsonUtils jsonUtils;

    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private InMemoryDB inMemoryDB;


    private final String UNKNOWN_ERROR = "UNKNOWN ERROR";


    @GetMapping("/getParticipants")
    public ResponseEntity<String> getParticipants() {
        try {
            val person = inMemoryDB.getParticipants();
            return responseUtils.createResponse(
                    jsonUtils.toJson(person),
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
        try {
            val person = inMemoryDB.getParticipantByName(name);
            return responseUtils.createResponse(
                    jsonUtils.toJson(person),
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
        try {
            val person = inMemoryDB.getParticipantById(id);
            return responseUtils.createResponse(
                    jsonUtils.toJson(person),
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
    public ResponseEntity<String> addParticipant(@RequestParam(value = "name") String name, @RequestParam(value = "giftIdeas") List<String> giftIdeas) {
        try {
            val dbResponse = inMemoryDB.addParticipant(name, giftIdeas);
            return responseUtils.createResponse(
                    jsonUtils.toJson(dbResponse),
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

    @PostMapping("/updateParticipantById")
    public ResponseEntity<String> updateParticipantById(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name, @RequestParam(value = "gift ideas") List<String> rawGiftIdeas) {
        try {
            val dbResponse = inMemoryDB.updateParticipantById(id, name, rawGiftIdeas);
            return responseUtils.createResponse(
                    jsonUtils.toJson(dbResponse),
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

    @PostMapping("/updateParticipantNameById")
    public ResponseEntity<String> updateParticipantById(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name) {
        try {
            val dbResponse = inMemoryDB.updateParticipantById(id, name);
            return responseUtils.createResponse(
                    jsonUtils.toJson(dbResponse),
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

    @PostMapping("/updateParticipantGiftIdeasById")
    public ResponseEntity<String> updateParticipantById(@RequestParam(value = "id") Long id, @RequestParam(value = "gift ideas") List<String> rawGiftIdeas) {
        try {
            val dbResponse = inMemoryDB.updateParticipantById(id, rawGiftIdeas);
            return responseUtils.createResponse(
                    jsonUtils.toJson(dbResponse),
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
        try {
            val dbResponse = inMemoryDB.removeParticipantById(id);
            return responseUtils.createResponse(
                    jsonUtils.toJson(dbResponse),
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
