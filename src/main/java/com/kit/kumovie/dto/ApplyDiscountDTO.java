package com.kit.kumovie.dto;

import com.kit.kumovie.domain.DiscountType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ApplyDiscountDTO {
    private Long screeningId;
    private BigDecimal discountValue;
    private DiscountType discountType;
}
