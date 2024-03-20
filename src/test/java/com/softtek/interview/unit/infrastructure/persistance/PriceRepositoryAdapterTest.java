package com.softtek.interview.unit.infrastructure.persistance;

import com.softtek.interview.domain.model.Price;
import com.softtek.interview.infrastructure.persistence.PriceRepositoryAdapter;
import com.softtek.interview.infrastructure.persistence.entity.PriceEntity;
import com.softtek.interview.infrastructure.persistence.repository.PriceRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PriceRepositoryAdapterTest {

    @Mock
    private PriceRepositoryJpa priceRepositoryJpa;

    @InjectMocks
    private PriceRepositoryAdapter priceRepositoryAdapter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findPricesOrderByPriority_whenReturnsAnEmptyList_thenNoPriceFound() {
        when(priceRepositoryJpa.findPriceByApplicationDateAndProductAndBrand(any(), any(), any()))
                .thenReturn(Optional.empty());

        Optional<Price> prices = priceRepositoryAdapter.findPricesOrderByPriority(
                LocalDateTime.now(), 1L, 1L);

        assertTrue(prices.isEmpty());

        verify(priceRepositoryJpa, times(1))
                .findPriceByApplicationDateAndProductAndBrand(any(), any(), any());
    }

    @Test
    public void findPricesOrderByPriority_whenReturnsPriceList_thenPricesFound() {
        Optional<PriceEntity> priceEntity = Optional.of(new PriceEntity());

        when(priceRepositoryJpa.findPriceByApplicationDateAndProductAndBrand(any(), any(), any()))
                .thenReturn(priceEntity);

        Optional<Price> prices = priceRepositoryAdapter.findPricesOrderByPriority(
                LocalDateTime.now(), 1L, 1L);

        assertTrue(prices.isPresent());

        verify(priceRepositoryJpa, times(1))
                .findPriceByApplicationDateAndProductAndBrand(any(), any(), any());
    }
}
