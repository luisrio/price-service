package com.itp.price_service.domain.model;

import java.time.Instant;

public record PriceInterval(Instant start, Instant end) {
    public PriceInterval {
        if (start == null || end == null)
            throw new IllegalArgumentException("Start date and End date cannot be null");

        if (end.isBefore(start))
            throw new IllegalArgumentException("End must not be before start");
    }

    public boolean contains(Instant instant) {
        return !instant.isBefore(start) && !instant.isAfter(end);
    }
}
