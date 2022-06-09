package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "select sum(t.seatCount) from Ticket t where (t.screening.screeningDate>= ?1 )and (t.screening.startTime >= ?2)")
    Long getTotalTicketCount(LocalDate screeningDate, LocalTime startTime);
}
