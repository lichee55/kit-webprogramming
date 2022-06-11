package com.kit.kumovie.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SeatDTO {
    private String row;
    private String col;

    public static List<SeatDTO> stringToSeatList(String seatNumber) {
        List<SeatDTO> seatList = new ArrayList<>();
        String[] seatArray = seatNumber.split(";");
        for (String seat : seatArray) {
            String[] seatInfo = seat.split(",");
            seatList.add(SeatDTO.builder()
                    .row(seatInfo[0])
                    .col(seatInfo[1])
                    .build());
        }
        return seatList;
    }
}
