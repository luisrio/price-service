package com.itp.price_service.domain.model;

import java.time.Instant;
import java.util.Objects;

public class Price {
    private final int priceList;
    private final BrandId brandId;
    private final ProductId productId;
    private final int priority;
    private final Money price;
    private final PriceInterval interval;

    public Price(int priceList, BrandId brandId, ProductId productId, int priority, Money price, PriceInterval interval) {
        if (brandId == null || productId == null || price == null || interval == null)
            throw new IllegalArgumentException("All fields are required");

        this.priceList = priceList;
        this.brandId = brandId;
        this.productId = productId;
        this.priority = priority;
        this.price = price;
        this.interval = interval;
    }

    public int priceList() {
        return priceList;
    }

    public BrandId brandId() {
        return brandId;
    }

    public ProductId productId() {
        return productId;
    }

    public int priority() {
        return priority;
    }

    public Money price() {
        return price;
    }

    public PriceInterval interval() {
        return interval;
    }

    public boolean isApplicable(Instant applicationDate) {
        return interval().contains(applicationDate);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Price price)) return false;
        return priceList == price.priceList;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(priceList);
    }

    @Override
    public String toString() {
        return "Price{" +
                "priceList=" + priceList +
                ", brandId=" + brandId +
                ", productId=" + productId +
                ", priority=" + priority +
                ", price=" + price +
                ", interval=" + interval +
                '}';
    }
}
