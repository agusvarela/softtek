package com.softtek.interview.infrastructure.adapter.mapper;

import com.softtek.interview.domain.model.Price;
import com.softtek.interview.infrastructure.adapter.dto.PriceResponseDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PriceMapper {

    public PriceResponseDto mapToPriceResponseDto(Price price) {
        if (Objects.isNull(price)) {
            throw new IllegalArgumentException("Price can not be null");
        }

        return PriceResponseDto.builder()
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .price(price.getPrice())
                .currency(price.getCurrency())
                .build();
    }
}
