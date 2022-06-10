package com.kit.kumovie.service;

import com.kit.kumovie.domain.Ticket;
import com.kit.kumovie.dto.BuyTicketDTO;
import com.kit.kumovie.dto.TicketListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {
    void buyTicket(BuyTicketDTO buyTicketDTO);

    Page<TicketListDTO> memberTicketList(Long memberId, Pageable pageable);
}
