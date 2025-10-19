package com.itp.price_service.application;

import com.itp.price_service.application.dto.ApplicablePriceResponse;
import com.itp.price_service.application.exception.PriceNotFoundException;
import com.itp.price_service.domain.model.BrandId;
import com.itp.price_service.domain.model.Price;
import com.itp.price_service.domain.model.ProductId;
import com.itp.price_service.domain.port.PriceRepository;
import com.itp.price_service.domain.service.PriceSelector;
import com.itp.price_service.application.dto.ApplicablePriceQuery;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class GetApplicablePrice {

    private final PriceRepository priceRepository;
    private final PriceSelector priceSelector;

    public GetApplicablePrice(PriceRepository priceRepository, PriceSelector priceSelector) {
        this.priceRepository = priceRepository;
        this.priceSelector = priceSelector;
    }

    /**
     * Executes the use case.
     * @param query input parameters (UTC date, product id, brand id)
     * @return applicable price mapped to response DTO
     * @throws PriceNotFoundException if no applicable price exists
     */
    public ApplicablePriceResponse execute(ApplicablePriceQuery query) {
        Instant dt = query.applicationDateUtc();
        ProductId productId = new ProductId(query.productId());
        BrandId brandId = new BrandId(query.brandId());

        List<Price> candidates = priceRepository.findApplicablePrices(brandId, productId, dt);
        Optional<Price> winner = priceSelector.selectHighestPriorityPrice(candidates, dt);

        return winner.map(GetApplicablePrice::mapToResponse)
                .orElseThrow(() -> new PriceNotFoundException(productId, brandId, dt));
    }

    private static ApplicablePriceResponse mapToResponse(Price p) {
        return new ApplicablePriceResponse(
                p.productId().value(),
                p.brandId().value(),
                p.priceList(),
                p.interval().start(),
                p.interval().end(),
                p.price().amount(),
                p.price().currency().getCurrencyCode()
        );
    }
}

