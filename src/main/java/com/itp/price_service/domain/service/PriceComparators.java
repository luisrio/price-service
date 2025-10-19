package com.itp.price_service.domain.service;

import com.itp.price_service.domain.model.Price;

import java.util.Comparator;

public class PriceComparators {

    /**
     * Comparator for choosing the most relevant price when multiple apply.
     * Rule of precedence:
     * 1) Higher priority value
     * 2) More recent start date
     * 3) Higher priceList ID (for deterministic tie-break)
     */
    public static final Comparator<Price> BY_PRIORITY_START_DATE_AND_PRICE_LIST =
            Comparator.comparingInt(Price::priority)
                    .thenComparing(p -> p.interval().start())
                    .thenComparing(Price::priceList);
}
