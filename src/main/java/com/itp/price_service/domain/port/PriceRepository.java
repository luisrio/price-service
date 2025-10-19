package com.itp.price_service.domain.port;

import com.itp.price_service.domain.model.BrandId;
import com.itp.price_service.domain.model.Price;
import com.itp.price_service.domain.model.ProductId;

import java.time.Instant;
import java.util.List;

public interface PriceRepository {
    List<Price> findApplicablePrices(BrandId brandId, ProductId productId, Instant applicationDateUtc);
}
