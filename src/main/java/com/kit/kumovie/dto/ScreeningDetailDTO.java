package com.kit.kumovie.dto;

import com.kit.kumovie.domain.DiscountType;
import com.kit.kumovie.domain.Screening;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Slf4j
@Data
public class ScreeningDetailDTO {
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private BigDecimal screeningPrice;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private Integer seatRowCount;
    private Integer seatColCount;
    private List<List<Boolean>> seatStatus;

    public static ScreeningDetailDTO of(Screening screening) {

        List<String> collect = List.of(screening.getSeatStatus().split(","));
        List<List<Boolean>> seatStatus = new ArrayList<>();
        Integer colCount = screening.getTheater().getColCount();
        Integer rowCount = screening.getTheater().getRowCount();

        if (collect.size() > 0) {
            for (int i = 0; i < rowCount; i++) {
                List<Boolean> row = new ArrayList<>();
                for (int j = 0; j < colCount; j++) {
                    if (collect.get(i * colCount + j).equals("1")) {
                        row.add(Boolean.TRUE);
                    } else {
                        row.add(Boolean.FALSE);
                    }
                }
                seatStatus.add(row);
            }
        }

        return ScreeningDetailDTO.builder()
                .id(screening.getId())
                .startTime(screening.getStartTime().toLocalTime())
                .endTime(screening.getEndTime().toLocalTime())
                .screeningPrice(screening.getScreeningPrice())
                .discountType(screening.getDiscountType())
                .discountValue(screening.getDiscountValue())
                .seatRowCount(rowCount)
                .seatColCount(colCount)
                .seatStatus(seatStatus)
                .build();
    }
}