package com.kit.kumovie.dto;

import com.kit.kumovie.domain.Ticket;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BuyTicketDTO {

    private Long screeningId;
    private Integer seatCount;
    private List<String> seatList;

    public static Ticket toEntity(BuyTicketDTO buyTicketDTO) {
        return Ticket.builder()
                .seatCount(buyTicketDTO.getSeatCount())
                .seatNumber(String.join(";", buyTicketDTO.getSeatList()))
                .build();
    }
}
