package com.softtek.interview.domain.port.in;

import com.softtek.interview.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface GetPriorityPricePort {

    Optional<Price> getPriorityPrice(LocalDateTime applicationDate, Long productId, Long brandId);

}
