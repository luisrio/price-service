package com.itp.price_service.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {

    @Autowired
    private MockMvc mvc;

    private static final String BASE = "/api/prices/applicable";

    @Test
    void test1_2020_06_14_10_00() throws Exception {
        mvc.perform(get(BASE)
                        .param("applicationDate", "2020-06-14T10:00:00Z")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.finalPrice").value(35.50));
    }

    @Test
    void test2_2020_06_14_16_00() throws Exception {
        mvc.perform(get(BASE)
                        .param("applicationDate", "2020-06-14T16:00:00Z")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.finalPrice").value(25.45));
    }

    @Test
    void test3_2020_06_14_21_00() throws Exception {
        mvc.perform(get(BASE)
                        .param("applicationDate", "2020-06-14T21:00:00Z")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.finalPrice").value(35.50));
    }

    @Test
    void test4_2020_06_15_10_00() throws Exception {
        mvc.perform(get(BASE)
                        .param("applicationDate", "2020-06-15T10:00:00Z")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.finalPrice").value(30.50));
    }

    @Test
    void test5_2020_06_16_21_00() throws Exception {
        mvc.perform(get(BASE)
                        .param("applicationDate", "2020-06-16T21:00:00Z")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.finalPrice").value(38.95));
    }

}