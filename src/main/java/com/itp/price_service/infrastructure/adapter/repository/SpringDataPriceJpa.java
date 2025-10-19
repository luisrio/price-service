package com.itp.price_service.infrastructure.adapter.repository;

import com.itp.price_service.infrastructure.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface SpringDataPriceJpa extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p WHERE p.brandId = :brandId AND p.productId = :productId " +
           "AND :dt BETWEEN p.startDate AND p.endDate " +
           "ORDER BY p.priority DESC, p.startDate DESC, p.priceList DESC")
    List<PriceEntity> findApplicableOrder(@Param("brandId") Long brandId,
                                          @Param("productId") Long productId,
                                          @Param("dt") Instant applicationDateUtc);
}
