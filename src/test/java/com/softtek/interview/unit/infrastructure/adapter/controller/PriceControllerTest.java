package com.softtek.interview.unit.infrastructure.adapter.controller;

import com.softtek.interview.domain.model.Price;
import com.softtek.interview.domain.port.in.GetPriorityPricePort;
import com.softtek.interview.infrastructure.adapter.controller.PriceController;
import com.softtek.interview.infrastructure.adapter.dto.PriceRequestDto;
import com.softtek.interview.infrastructure.adapter.dto.PriceResponseDto;
import com.softtek.interview.infrastructure.adapter.mapper.PriceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PriceControllerTest {

    @Mock
    private GetPriorityPricePort getPriorityPricePort;

    @Mock
    private PriceMapper priceMapper;

    @InjectMocks
    private PriceController priceController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getPriorityPrice_whenPriceFound_thenReturnsSuccessfulResponse() {
        PriceResponseDto priceResponseDto = new PriceResponseDto();

        when(getPriorityPricePort.getPriorityPrice(any(), any(), any()))
                .thenReturn(Optional.of(new Price()));

        when(priceMapper.mapToPriceResponseDto(any())).thenReturn(priceResponseDto);

        ResponseEntity<PriceResponseDto> response = priceController.getPriorityPrice(new PriceRequestDto());

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(priceResponseDto, response.getBody());

        verify(priceMapper, times(1)).mapToPriceResponseDto(any());
        verify(getPriorityPricePort, times(1)).getPriorityPrice(any(), any(), any());
    }

    @Test
    public void getPriorityPrice_whenPriceNotFound_thenReturnsNotFoundResponse() {
        when(getPriorityPricePort.getPriorityPrice(any(), any(), any()))
                .thenReturn(Optional.empty());

        ResponseEntity<PriceResponseDto> response = priceController.getPriorityPrice(new PriceRequestDto());

        assertTrue(response.getStatusCode().is4xxClientError());

        verify(getPriorityPricePort, times(1)).getPriorityPrice(any(), any(), any());
    }
}
