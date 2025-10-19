package com.itp.price_service.application.exception;

import com.itp.price_service.domain.model.BrandId;
import com.itp.price_service.domain.model.ProductId;

import java.time.Instant;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(ProductId productId, BrandId brandId, Instant at) {
        super("No applicable price for product=" + productId.value() +
                ", brand=" + brandId.value() +
                " at=" + at.toString());
    }
}
