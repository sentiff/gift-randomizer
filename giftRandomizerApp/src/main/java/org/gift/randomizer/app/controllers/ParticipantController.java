package org.gift.randomizer.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.val;
import org.gift.randomizer.app.db.NoCandidateException;
import org.gift.randomizer.app.db.InMemoryDB;
import org.gift.randomizer.app.utils.JsonUtils;
import org.gift.randomizer.app.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParticipantController {

    private final String APPLICATION_JSON = "application/json";
    private final String TEXT_PLAIN = "text/plain";

    @Autowired
    private JsonUtils jsonUtils;

    @Autowired
    private ResponseUtils responseUtils;

    @Autowired
    private InMemoryDB inMemoryDB;

    @GetMapping("/getParticipantByName")
    public ResponseEntity<String> getParticipantByName(@RequestParam(value = "name") String name) {
        try {
            val person = inMemoryDB.getParticipantByName(name);
            return responseUtils.createResponse(
                    jsonUtils.toJson(person),
                    APPLICATION_JSON,
                    HttpStatus.OK
            );
        } catch (NoCandidateException | JsonProcessingException e) {
            return responseUtils.createResponse(
                    e.getMessage(),
                    TEXT_PLAIN,
                    HttpStatus.BAD_GATEWAY
            );
        }
    }

    @GetMapping("/getParticipantById")
    public ResponseEntity<String> getParticipantById(@RequestParam(value = "id", defaultValue = "1") Long id) {
        try {
            val person = inMemoryDB.getParticipantById(id);
            return responseUtils.createResponse(
                    jsonUtils.toJson(person),
                    APPLICATION_JSON,
                    HttpStatus.OK
            );
        } catch (NoCandidateException | JsonProcessingException e) {
            return responseUtils.createResponse(
                    e.getMessage(),
                    TEXT_PLAIN,
                    HttpStatus.BAD_GATEWAY
            );
        }
    }

    @PostMapping("/addParticipant")
    public ResponseEntity<String> addParticipant(@RequestParam(value = "name") String name, @RequestParam(value = "giftIdeas") List<String> giftIdeas) {
        try {
            val dbResponse = inMemoryDB.addParticipant(name, giftIdeas);
            return responseUtils.createResponse(
                    jsonUtils.toJson(dbResponse),
                    APPLICATION_JSON,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return responseUtils.createResponse(
                    e.getMessage(),
                    TEXT_PLAIN,
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
                    APPLICATION_JSON,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return responseUtils.createResponse(
                    e.getMessage(),
                    TEXT_PLAIN,
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
                    APPLICATION_JSON,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return responseUtils.createResponse(
                    e.getMessage(),
                    TEXT_PLAIN,
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
                    APPLICATION_JSON,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return responseUtils.createResponse(
                    e.getMessage(),
                    TEXT_PLAIN,
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
                    APPLICATION_JSON,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return responseUtils.createResponse(
                    e.getMessage(),
                    TEXT_PLAIN,
                    HttpStatus.BAD_GATEWAY
            );
        }
    }
}
