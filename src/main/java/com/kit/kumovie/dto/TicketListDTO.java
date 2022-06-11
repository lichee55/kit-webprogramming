package com.kit.kumovie.dto;

import com.kit.kumovie.domain.Ticket;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class TicketListDTO {

    private Long id;
    private Integer seatCount;
    private BigDecimal price;
    private ScreeningListDTO screening;
    private List<SeatDTO> seatNumber; // 0,1;0,2;0,3;... (divide by ';')(count = seatCount)

    public static TicketListDTO of(Ticket ticket) {

        List<SeatDTO> seatDTOS = SeatDTO.stringToSeatList(ticket.getSeatNumber());

        return TicketListDTO.builder()

                .id(ticket.getId())
                .seatCount(ticket.getSeatCount())
                .price(ticket.getPrice())
                .screening(ScreeningListDTO.of(ticket.getScreening()))
                .seatNumber(seatDTOS)
                .build();
    }
}
