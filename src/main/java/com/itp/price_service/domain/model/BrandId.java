package com.itp.price_service.domain.model;

public record BrandId(long value) {
    public BrandId{
        if (value < 0) {
            throw new IllegalArgumentException("BrandId must be a positive integer");
        }
    }
}
