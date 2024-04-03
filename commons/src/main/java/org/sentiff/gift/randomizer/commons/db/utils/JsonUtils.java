package org.sentiff.gift.randomizer.commons.db.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    final ObjectMapper objectMapper = new ObjectMapper();

    public String toJson(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

}
