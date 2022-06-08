package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
