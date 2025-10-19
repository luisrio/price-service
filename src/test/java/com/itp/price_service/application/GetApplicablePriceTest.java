package com.itp.price_service.application;

import com.itp.price_service.application.dto.ApplicablePriceQuery;
import com.itp.price_service.application.dto.ApplicablePriceResponse;
import com.itp.price_service.domain.model.*;
import com.itp.price_service.domain.port.PriceRepository;
import com.itp.price_service.domain.service.PriceSelector;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GetApplicablePriceTest {

    private static Price price(int list, int priority, String start, String end, double amount) {
        Money money = new Money(BigDecimal.valueOf(amount), Currency.getInstance("EUR"));
        PriceInterval interval = new PriceInterval(Instant.parse(start), Instant.parse(end));
        return new Price(list, new BrandId(1L), new ProductId(35455L), priority, money, interval);
    }

    @Test
    void returns_winner_as_response() {
        PriceRepository repo = Mockito.mock(PriceRepository.class);
        PriceSelector selector = new PriceSelector();
        GetApplicablePrice usecase = new GetApplicablePrice(repo, selector);

        Instant at = Instant.parse("2020-06-14T16:00:00Z");

        Price a = price(1, 0, "2020-06-14T00:00:00Z", "2020-12-31T23:59:59Z", 35.50);
        Price b = price(2, 1, "2020-06-14T15:00:00Z", "2020-06-14T18:30:00Z", 25.45);

        when(repo.findApplicablePrices(new BrandId(1L), new ProductId(35455L), at)).thenReturn(List.of(a, b));

        ApplicablePriceResponse resp = usecase.execute(new ApplicablePriceQuery(at, 35455L, 1L));
        assertEquals(2, resp.priceList());
        assertEquals("EUR", resp.currency());
    }
}