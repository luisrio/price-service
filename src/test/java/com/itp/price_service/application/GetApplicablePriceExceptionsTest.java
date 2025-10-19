package com.itp.price_service.application;

import com.itp.price_service.application.dto.ApplicablePriceQuery;
import com.itp.price_service.application.exception.PriceNotFoundException;
import com.itp.price_service.domain.model.BrandId;
import com.itp.price_service.domain.model.ProductId;
import com.itp.price_service.domain.port.PriceRepository;
import com.itp.price_service.domain.service.PriceSelector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class GetApplicablePriceExceptionsTest {

    @Test
    @DisplayName("Use case throws PriceNotFoundException when repository returns no candidates")
    void throwsNotFoundWhenEmpty() {
        PriceRepository repo = Mockito.mock(PriceRepository.class);
        PriceSelector selector = new PriceSelector();
        GetApplicablePrice usecase = new GetApplicablePrice(repo, selector);

        Instant at = Instant.parse("2021-01-01T00:00:00Z");
        when(repo.findApplicablePrices(new BrandId(1L), new ProductId(35455L), at))
                .thenReturn(Collections.emptyList());

        assertThrows(PriceNotFoundException.class,
                () -> usecase.execute(new ApplicablePriceQuery(at, 35455L, 1L)));
    }


    @Test
    @DisplayName("Use case propagates IllegalArgumentException when brandId is invalid (<=0)")
    void invalidBrandId_throwsIllegalArgument() {
        PriceRepository repo = Mockito.mock(PriceRepository.class);
        PriceSelector selector = new PriceSelector();
        GetApplicablePrice usecase = new GetApplicablePrice(repo, selector);

        Instant at = Instant.parse("2020-06-14T10:00:00Z");

        assertThrows(IllegalArgumentException.class,
                () -> usecase.execute(new ApplicablePriceQuery(at, 35455L, -1L)));
    }


    @Test
    @DisplayName("Use case propagates IllegalArgumentException when productId is invalid (<=0)")
    void invalidProductId_throwsIllegalArgument() {
        PriceRepository repo = Mockito.mock(PriceRepository.class);
        PriceSelector selector = new PriceSelector();
        GetApplicablePrice usecase = new GetApplicablePrice(repo, selector);

        Instant at = Instant.parse("2020-06-14T10:00:00Z");

        assertThrows(IllegalArgumentException.class,
                () -> usecase.execute(new ApplicablePriceQuery(at, -1L, 1L)));
    }
}
