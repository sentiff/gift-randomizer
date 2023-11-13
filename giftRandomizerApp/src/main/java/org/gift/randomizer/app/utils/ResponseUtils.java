package org.gift.randomizer.app.utils;

import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;

public class ResponseUtils {

    public ResponseEntity<String> createResponse(String body, String contentType, HttpStatus status) {
        return new ResponseEntity<>(
                body,
                createHeaders(contentType),
                status
        );
    }

    private LinkedMultiValueMap<String, String> createHeaders(String contentType) {
        val responseHeaders = new LinkedMultiValueMap<String, String>();
        responseHeaders.add("Content-Type", contentType);
        return responseHeaders;
    }
}
