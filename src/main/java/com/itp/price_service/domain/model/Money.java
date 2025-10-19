package com.itp.price_service.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import static java.math.BigDecimal.*;

public record Money(BigDecimal amount, Currency currency) {
    public Money {
        if (amount == null || currency == null)
            throw new IllegalArgumentException("Amount and currency cannot be null");

        amount = amount.setScale(2, ROUND_HALF_UP);
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency=" + currency.getCurrencyCode() +
                '}';
    }
}
