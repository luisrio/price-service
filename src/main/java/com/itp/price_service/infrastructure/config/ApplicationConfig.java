package com.itp.price_service.infrastructure.config;

import com.itp.price_service.application.GetApplicablePrice;
import com.itp.price_service.domain.port.PriceRepository;
import com.itp.price_service.domain.service.PriceSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public PriceSelector priceSelector() {
        return new PriceSelector();
    }

    @Bean
    public GetApplicablePrice getApplicablePrice(PriceRepository priceRepository, PriceSelector priceSelector) {
        return new GetApplicablePrice(priceRepository, priceSelector);
    }
}
