package com.itp.price_service.application.dto;

import java.time.Instant;

public record ApplicablePriceQuery(Instant applicationDateUtc, Long productId, Long brandId) {
}
