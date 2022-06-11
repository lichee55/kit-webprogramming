package com.kit.kumovie.dto;

import com.kit.kumovie.domain.Ticket;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class BuyTicketDTO {

    private Long screeningId;
    private List<SeatDTO> seatList;

    public static Ticket toEntity(BuyTicketDTO buyTicketDTO) {
        return Ticket.builder()
                .seatNumber(String.join(";", buyTicketDTO.seatList()))
                .build();
    }

    public List<String> seatList() {
        return this.seatList.stream().map(e -> e.getRow() + "," + e.getCol()).collect(Collectors.toList());
    }
}
