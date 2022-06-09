package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "select sum(t.seatCount) from Ticket t where (t.screening.startTime >= ?1) ")
    Long getTotalTicketCount(LocalDateTime startTime);
}
