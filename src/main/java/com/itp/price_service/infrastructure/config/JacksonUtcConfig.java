package com.itp.price_service.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneOffset;
import java.util.TimeZone;

@Configuration
public class JacksonUtcConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
        return mapper;
    }
}
