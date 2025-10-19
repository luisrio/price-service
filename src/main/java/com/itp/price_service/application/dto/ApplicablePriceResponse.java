package com.itp.price_service.application.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record ApplicablePriceResponse(Long productId,
                                      Long brandId,
                                      Integer priceList,
                                      Instant startDateUtc,
                                      Instant endDateUtc,
                                      BigDecimal finalPrice,
                                      String currency) {
}
