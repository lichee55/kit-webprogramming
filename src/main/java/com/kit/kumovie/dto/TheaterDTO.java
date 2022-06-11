package com.kit.kumovie.dto;

import com.kit.kumovie.domain.Theater;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TheaterDTO {

    private Long id;
    private String name;
    private Integer floorCount;
    private Integer totalSeat;

    public static TheaterDTO of(Theater theater) {
        return TheaterDTO.builder()
                .id(theater.getId())
                .name(theater.getName())
                .floorCount(theater.getFloorCount())
                .totalSeat(theater.getColCount() * theater.getRowCount())
                .build();
    }
}
