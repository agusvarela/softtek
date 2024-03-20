package com.softtek.interview.unit.infrastructure.persistance;

import com.softtek.interview.domain.model.Price;
import com.softtek.interview.infrastructure.persistence.PriceEntityMapper;
import com.softtek.interview.infrastructure.persistence.entity.PriceEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PriceEntityMapperTest {

    @Test
    public void mapToPrice_thenReturnsCorrectPrice() {
        Optional<PriceEntity> priceEntity = Optional.of(buildPriceEntity(1L));

        Optional<Price> price = PriceEntityMapper.mapToPrice(priceEntity);

        assertTrue(price.isPresent());
        assertEquals(priceEntity.get().getProductId(), price.get().getProductId());
    }


    private PriceEntity buildPriceEntity(Long productId) {
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setProductId(productId);
        priceEntity.setBrandId(2L);
        priceEntity.setPriceList(3);
        priceEntity.setStartDate(LocalDateTime.now());
        priceEntity.setEndDate(LocalDateTime.now().plusDays(1));
        priceEntity.setPrice(BigDecimal.valueOf(35.50));
        priceEntity.setCurrency("EUR");
        return priceEntity;
    }
}
