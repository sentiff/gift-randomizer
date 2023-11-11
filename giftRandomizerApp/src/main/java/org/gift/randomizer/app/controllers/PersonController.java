package org.gift.randomizer.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.val;
import org.gift.randomizer.app.db.NoCandidateException;
import org.gift.randomizer.app.db.InMemoryDB;
import org.gift.randomizer.app.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private final String APPLICATION_JSON = "application/json";
    private final String TEXT_PLAIN = "text/plain";

    @Autowired
    private JsonUtils jsonUtils;

    @Autowired
    private InMemoryDB inMemoryDB;

    @PostMapping("/getPersonByName")
    public ResponseEntity<String> getPersonByName(String name) {
        try {
            val person = inMemoryDB.getPersonByName(name);
            return createResponse(
                    jsonUtils.toJson(person),
                    APPLICATION_JSON,
                    HttpStatus.OK
            );
        } catch (NoCandidateException | JsonProcessingException e) {
            return createResponse(
                    e.getMessage(),
                    TEXT_PLAIN,
                    HttpStatus.BAD_GATEWAY
            );
        }
    }

    @GetMapping("/getPersonById")
    public ResponseEntity<String> getPersonById(@RequestParam(value = "id", defaultValue = "1") Long id) {
        try {
            val person = inMemoryDB.getPersonById(id);
            return createResponse(
                    jsonUtils.toJson(person),
                    APPLICATION_JSON,
                    HttpStatus.OK
            );
        } catch (NoCandidateException | JsonProcessingException e) {
            return createResponse(
                    e.getMessage(),
                    TEXT_PLAIN,
                    HttpStatus.BAD_GATEWAY
            );
        }
    }

    @PostMapping("/addPerson")
    public ResponseEntity<String> addPerson(@RequestParam(value = "name") String name, @RequestParam(value = "giftIdeas") List<String> giftIdeas) {
        try {
            val dbResponse = inMemoryDB.addPerson(name, giftIdeas);
            return createResponse(
                    jsonUtils.toJson(dbResponse),
                    APPLICATION_JSON,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return createResponse(
                    e.getMessage(),
                    TEXT_PLAIN,
                    HttpStatus.BAD_GATEWAY
            );
        }
    }

    @PostMapping("/updatePersonById")
    public ResponseEntity<String> updatePersonById(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name, @RequestParam(value = "gift ideas") List<String> rawGiftIdeas) {
        try {
            val dbResponse = inMemoryDB.updatePersonById(id, name, rawGiftIdeas);
            return createResponse(
                    jsonUtils.toJson(dbResponse),
                    APPLICATION_JSON,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return createResponse(
                    e.getMessage(),
                    TEXT_PLAIN,
                    HttpStatus.BAD_GATEWAY
            );
        }
    }

    @PostMapping("/updatePersonNameById")
    public ResponseEntity<String> updatePersonById(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name) {
        try {
            val dbResponse = inMemoryDB.updatePersonById(id, name);
            return createResponse(
                    jsonUtils.toJson(dbResponse),
                    APPLICATION_JSON,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return createResponse(
                    e.getMessage(),
                    TEXT_PLAIN,
                    HttpStatus.BAD_GATEWAY
            );
        }
    }

    @PostMapping("/updatePersonGiftIdeasById")
    public ResponseEntity<String> updatePersonById(@RequestParam(value = "id") Long id, @RequestParam(value = "gift ideas") List<String> rawGiftIdeas) {
        try {
            val dbResponse = inMemoryDB.updatePersonById(id, rawGiftIdeas);
            return createResponse(
                    jsonUtils.toJson(dbResponse),
                    APPLICATION_JSON,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return createResponse(
                    e.getMessage(),
                    TEXT_PLAIN,
                    HttpStatus.BAD_GATEWAY
            );
        }
    }

    @DeleteMapping("/removePersonById")
    public ResponseEntity<String> removePersonById(@RequestParam(value = "id") Long id) {
        try {
            val dbResponse = inMemoryDB.removePersonById(id);
            return createResponse(
                    jsonUtils.toJson(dbResponse),
                    APPLICATION_JSON,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return createResponse(
                    e.getMessage(),
                    TEXT_PLAIN,
                    HttpStatus.BAD_GATEWAY
            );
        }
    }


    private LinkedMultiValueMap<String, String> createHeaders(String contentType) {
        val responseHeaders = new LinkedMultiValueMap<String, String>();
        responseHeaders.add("Content-Type", contentType);
        return responseHeaders;
    }

    private ResponseEntity<String> createResponse(String body, String contentType, HttpStatus status) {
        return new ResponseEntity<>(
                body,
                createHeaders(contentType),
                status
        );
    }
}
