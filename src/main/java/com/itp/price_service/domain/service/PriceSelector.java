package com.itp.price_service.domain.service;

import com.itp.price_service.domain.model.Price;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class PriceSelector {

    /**
     * Returns the price applicable at the given date with the highest precedence.
     *
     * @param prices list of candidate prices
     * @param applicationDate instant in UTC for which the price should apply
     * @return the best applicable price, or empty if none apply
     */
    public Optional<Price> selectHighestPriorityPrice(List<Price> prices, Instant applicationDate) {
        if (prices == null || prices.isEmpty()) return Optional.empty();

        return prices.stream()
                .filter(p -> p.isApplicable(applicationDate))
                .max(PriceComparators.BY_PRIORITY_START_DATE_AND_PRICE_LIST);
    }
}
