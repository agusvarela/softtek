package com.softtek.interview.infrastructure.adapter.controller;

import com.softtek.interview.domain.model.Price;
import com.softtek.interview.domain.port.in.GetPriorityPricePort;
import com.softtek.interview.infrastructure.adapter.dto.PriceRequestDto;
import com.softtek.interview.infrastructure.adapter.dto.PriceResponseDto;
import com.softtek.interview.infrastructure.adapter.mapper.PriceMapper;
import com.softtek.interview.infrastructure.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@Tag(name = "PriceController", description = "The Price API")
public class PriceController {

    private final GetPriorityPricePort getPriorityPricePort;
    private final PriceMapper priceMapper;

    public PriceController(GetPriorityPricePort getPriorityPricePort, PriceMapper priceMapper) {
        this.getPriorityPricePort = getPriorityPricePort;
        this.priceMapper = priceMapper;
    }

    @Operation(summary = "Get Priority Price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PriceResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) }),
            @ApiResponse(responseCode = "404", description = "Price not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)) })
    })
    @GetMapping("/priority-price")
    public ResponseEntity<PriceResponseDto> getPriorityPrice(@Valid @ModelAttribute PriceRequestDto requestDto) {
        Optional<Price> price = getPriorityPricePort
                .getPriorityPrice(requestDto.getApplicationDate(), requestDto.getProductId(), requestDto.getBrandId());

        return price
                .map(value -> ResponseEntity.ok(priceMapper.mapToPriceResponseDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
