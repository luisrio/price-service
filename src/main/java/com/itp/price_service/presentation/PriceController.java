package com.itp.price_service.presentation;

import com.itp.price_service.application.GetApplicablePrice;
import com.itp.price_service.application.dto.ApplicablePriceResponse;
import com.itp.price_service.application.dto.ApplicablePriceQuery;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/prices")
@Validated
public class PriceController {

    private final GetApplicablePrice getApplicablePrice;

    public PriceController(GetApplicablePrice getApplicablePrice) {
        this.getApplicablePrice = getApplicablePrice;
    }

    @GetMapping("applicable")
    public ApplicablePriceResponse getApplicablePrice(@RequestParam("applicationDate") @NotNull Instant applicationDateUtc,
                                                      @RequestParam("productId") @NotNull Long productId,
                                                      @RequestParam("brandId") @NotNull Long brandId) {

        ApplicablePriceQuery query = new ApplicablePriceQuery(applicationDateUtc, productId, brandId);
        return getApplicablePrice.execute(query);
    }
}
