package com.spacecodee.springbootsecurityopentemplate.config.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class StringToMapConverter implements Converter<String, Map<String, String>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, String> convert(@NotNull String source) {
        if (source.trim().isEmpty()) {
            return new HashMap<>();
        }

        try {
            TypeReference<HashMap<String, String>> typeRef = new TypeReference<>() {
            };
            return objectMapper.readValue(source, typeRef);
        } catch (JsonProcessingException e) {
            log.error("Error converting string to map: {}", source, e);
            throw new IllegalArgumentException("Invalid metadata format: " + source);
        } catch (Exception e) {
            log.error("Unexpected error converting string to map: {}", source, e);
            return new HashMap<>();
        }
    }
}
