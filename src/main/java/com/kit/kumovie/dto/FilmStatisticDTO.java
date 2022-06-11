package com.kit.kumovie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmStatisticDTO {
    BigDecimal maleRate;
    BigDecimal femaleRate;
    BigDecimal age10Rate;
    BigDecimal age20Rate;
    BigDecimal age30Rate;
    BigDecimal age40Rate;
    BigDecimal age50Rate;

    public FilmStatisticDTO(BigDecimal bigDecimal) {
        this.maleRate = bigDecimal;
        this.femaleRate = bigDecimal;
        this.age10Rate = bigDecimal;
        this.age20Rate = bigDecimal;
        this.age30Rate = bigDecimal;
        this.age40Rate = bigDecimal;
        this.age50Rate = bigDecimal;
    }
}
