package com.epam.esm.api.integration.util;

import com.epam.esm.core.entity.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Set;
import java.util.StringJoiner;

public class JsonUtils {

    public static String toJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }

    public static <T> T toObject(String jsonString, Class<T> returned) throws JsonProcessingException {
        return new ObjectMapper().readValue(jsonString, returned);
    }

}
