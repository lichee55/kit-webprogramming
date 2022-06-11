package com.kit.kumovie.dto;

import com.kit.kumovie.domain.Screening;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Slf4j
@Data
public class ScreeningDetailDTO {
    private Long id;
    private String startTime;
    private String endTime;
    private String screeningPrice;
    private String discountType;
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
                .startTime(screening.getStartTime().toString())
                .endTime(screening.getEndTime().toString())
                .screeningPrice(screening.getScreeningPrice().toString())
                .discountType(screening.getDiscountType().toString())
                .seatStatus(seatStatus)
                .build();
    }
}
