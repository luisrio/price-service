package com.itp.price_service.domain.model;

public record ProductId(long value) {
    public ProductId {
        if (value < 0) throw new IllegalArgumentException("Product id cannot be negative");
    }
}
