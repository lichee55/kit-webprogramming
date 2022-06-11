package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "select sum(t.seatCount) from Ticket t where (t.screening.startTime >= ?1) ")
    Long getTotalTicketCount(LocalDateTime startTime);

    Page<Ticket> findAllByMember_Id(Long memberId, Pageable pageable);

    @Query(value = "select sum(t.seatCount) from Ticket t where (t.screening.startTime >= ?1) and (t.screening.film" +
            ".id = ?2)")
    Integer getSeatCount(LocalDateTime startTime, Long filmId);
}
