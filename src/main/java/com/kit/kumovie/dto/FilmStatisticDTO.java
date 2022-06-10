package com.kit.kumovie.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class FilmStatisticDTO {
    Long id;
    BigDecimal maleRate;
    BigDecimal femaleRate;
    BigDecimal age10Rate;
    BigDecimal age20Rate;
    BigDecimal age30Rate;
    BigDecimal age40Rate;
    BigDecimal age50Rate;
}
