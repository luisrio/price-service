package com.itp.price_service.infrastructure.adapter;

import com.itp.price_service.domain.model.*;
import com.itp.price_service.domain.port.PriceRepository;
import com.itp.price_service.infrastructure.entity.PriceEntity;
import com.itp.price_service.infrastructure.adapter.repository.SpringDataPriceJpa;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Currency;
import java.util.List;

@Repository
public class PriceRepositoryAdapter implements PriceRepository {

    private final SpringDataPriceJpa priceRepository;

    public PriceRepositoryAdapter(SpringDataPriceJpa priceRepository) {
        this.priceRepository = priceRepository;
    }


    @Override
    public List<Price> findApplicablePrices(BrandId brandId, ProductId productId, Instant applicationDateUtc) {
        List<PriceEntity> rows = priceRepository.findApplicableOrder(brandId.value(), productId.value(), applicationDateUtc);
        return rows.stream().map(PriceRepositoryAdapter::toDomain).toList();
    }

    private static Price toDomain(PriceEntity p) {
        PriceInterval interval = new PriceInterval(p.getStartDate(), p.getEndDate());
        Money money = new Money(p.getPrice(), Currency.getInstance(p.getCurrency()));
        return new Price(p.getPriceList(),new BrandId(p.getBrandId()), new ProductId(p.getProductId()), p.getPriority(), money, interval);
    }
}
