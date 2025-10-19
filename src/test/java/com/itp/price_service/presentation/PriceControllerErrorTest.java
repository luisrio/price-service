package com.itp.price_service.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerErrorTest {

    @Autowired
    private MockMvc mvc;


    private static final String BASE = "/api/prices/applicable";


    @Test
    @DisplayName("400 when a required parameter is missing")
    void missingParameter_returns400() throws Exception {
        mvc.perform(get(BASE)
                        .param("applicationDate", "2020-06-14T16:00:00Z")
// .param("productId", "35455") // intentionally missing
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("400 when applicationDate is not ISO-8601")
    void invalidDateFormat_returns400() throws Exception {
        mvc.perform(get(BASE)
                        .param("applicationDate", "not-a-date")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("400 when brandId is non-positive (IllegalArgumentException from domain)")
    void nonPositiveBrand_returns400() throws Exception {
        mvc.perform(get(BASE)
                        .param("applicationDate", "2020-06-14T10:00:00Z")
                        .param("productId", "35455")
                        .param("brandId", "-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON));
    }


    @Test
    @DisplayName("404 when no price is applicable for the given instant")
    void noApplicablePrice_returns404() throws Exception {
// Outside all intervals in data.sql
        mvc.perform(get(BASE)
                        .param("applicationDate", "2019-01-01T00:00:00Z")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON));
    }

}
