package com.softtek.interview.infrastructure.persistence;

import com.softtek.interview.domain.model.Price;
import com.softtek.interview.infrastructure.persistence.entity.PriceEntity;
import org.modelmapper.ModelMapper;

import java.util.Optional;

public class PriceEntityMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Optional<Price> mapToPrice(Optional<PriceEntity> priceEntityOptional) {
        return priceEntityOptional.map(priceEntity -> modelMapper.map(priceEntity, Price.class));
    }
}
