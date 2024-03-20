package com.softtek.interview.infrastructure.adapter.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceRequestDto {

    @NotNull
    @Min(value = 1, message = "Product ID must be a positive number")
    private Long productId;

    @NotNull
    @Min(value = 1, message = "Brand ID must be a positive number")
    private Long brandId;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime applicationDate;

}