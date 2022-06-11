package com.kit.kumovie.dto;

import com.kit.kumovie.domain.Screening;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
public class ScreeningListDTO {
    private Long id;
    private LocalDate screeningDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer restSeatCount;
    private TheaterDTO theater;

    public static ScreeningListDTO of(Screening screening) {
        // restSeat 는 내부에서 계산할 수 없으므로 외부에서 계산해야 한다.
        return ScreeningListDTO.builder()
                .id(screening.getId())
                .screeningDate(screening.getStartTime().toLocalDate())
                .startTime(screening.getStartTime().toLocalTime())
                .endTime(screening.getEndTime().toLocalTime())
                .restSeatCount(screening.getRestSeatCount())
                .theater(TheaterDTO.of(screening.getTheater()))
                .build();
    }
}
