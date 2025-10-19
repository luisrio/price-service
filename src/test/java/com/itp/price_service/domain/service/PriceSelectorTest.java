package com.itp.price_service.domain.service;

import com.itp.price_service.domain.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class PriceSelectorTest {

    private static Price price(int list, int priority, String start, String end, double amount) {
        Money money = new Money(BigDecimal.valueOf(amount), Currency.getInstance("EUR"));
        PriceInterval interval = new PriceInterval(Instant.parse(start), Instant.parse(end));
        return new Price(list, new BrandId(1L), new ProductId(35455L), priority, money, interval);
    }

    @Test
    void picks_highest_priority_then_latest_start_then_highest_list() {
        Price a = price(1, 0, "2020-06-14T00:00:00Z", "2020-12-31T23:59:59Z", 35.50);
        Price b = price(2, 1, "2020-06-14T15:00:00Z", "2020-06-14T18:30:00Z", 25.45);
        Price c = price(3, 1, "2020-06-15T00:00:00Z", "2020-06-15T11:00:00Z", 30.50);

        Instant at = Instant.parse("2020-06-14T16:00:00Z");
        PriceSelector selector = new PriceSelector();
        Optional<Price> winner = selector.selectHighestPriorityPrice(List.of(a,b,c), at);
        assertTrue(winner.isPresent());
        assertEquals(2, winner.get().priceList());
    }

    @Test
    void comparator_orders_as_expected() {
        Price a = price(1, 0, "2020-06-14T00:00:00Z", "2020-12-31T23:59:59Z", 35.50);
        Price b = price(2, 1, "2020-06-14T15:00:00Z", "2020-06-14T18:30:00Z", 25.45);
        int cmp = PriceComparators.BY_PRIORITY_START_DATE_AND_PRICE_LIST.compare(a, b);
        assertTrue(cmp > 0 == false); // b should be before a (higher priority)
        assertTrue(cmp < 0);
    }

}