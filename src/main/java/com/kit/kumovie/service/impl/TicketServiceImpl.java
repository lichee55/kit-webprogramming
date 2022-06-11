package com.kit.kumovie.service.impl;

import com.kit.kumovie.common.Common;
import com.kit.kumovie.domain.*;
import com.kit.kumovie.domain.repository.FilmRepository;
import com.kit.kumovie.domain.repository.MemberRepository;
import com.kit.kumovie.domain.repository.ScreeningRepository;
import com.kit.kumovie.domain.repository.TicketRepository;
import com.kit.kumovie.dto.BuyTicketDTO;
import com.kit.kumovie.dto.SeatDTO;
import com.kit.kumovie.dto.TicketListDTO;
import com.kit.kumovie.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final ScreeningRepository screeningRepository;
    private final MemberRepository memberRepository;
    private final TicketRepository ticketRepository;
    private final FilmRepository filmRepository;

    @Override
    @Transactional
    public void buyTicket(BuyTicketDTO buyTicketDTO) {

        Member userContext = Common.getUserContext();
        Screening screening = screeningRepository.findById(buyTicketDTO.getScreeningId()).orElseThrow(() -> new RuntimeException("상영 정보가 유효하지 않습니다."));
        Integer colCount = screening.getTheater().getColCount();
        Film film = screening.getFilm();

        BigDecimal price = screening.getScreeningPrice();
        if (screening.getDiscountType().equals(DiscountType.AMOUNT)) {
            price = price.subtract(screening.getDiscountValue());
        } else if (screening.getDiscountType().equals(DiscountType.RATE)) {
            price = price.subtract(price.multiply(screening.getDiscountValue()).divide(BigDecimal.valueOf(100), 0, RoundingMode.FLOOR));
        }

        BigDecimal ticketPrice = price.multiply(BigDecimal.valueOf(buyTicketDTO.getSeatList().size()));

        Ticket ticket = BuyTicketDTO.toEntity(buyTicketDTO);
        ticket.setScreening(screening);
        ticket.setSeatCount(buyTicketDTO.getSeatList().size());
        ticket.setMember(memberRepository.findById(userContext.getId()).orElseThrow(() -> new RuntimeException("회원 정보가 유효하지 않습니다.")));
        ticket.setPrice(ticketPrice);
        LocalDateTime now = LocalDateTime.now();
        ticket.setCreatedAt(now);
        ticket.setUpdatedAt(now);
        Integer seatCount = ticketRepository.getSeatCount(now, film.getId());
        film.setSeatCount(seatCount);

        List<String> seatStatus = Arrays.stream(screening.getSeatStatus().split(",")).collect(Collectors.toList());
        for (SeatDTO seat : buyTicketDTO.getSeatList()) {
            seatStatus.set(Integer.parseInt(seat.getRow()) * colCount + Integer.parseInt(seat.getCol()), "1");
        }
        String seatStatusStr = String.join(",", seatStatus);
        screening.setSeatStatus(seatStatusStr);
        screening.setRestSeatCount(screening.getRestSeatCount() - buyTicketDTO.getSeatList().size());

        ticketRepository.save(ticket);
        screeningRepository.save(screening);
        filmRepository.save(film);
    }

    @Override
    public Page<TicketListDTO> memberTicketList(Long memberId, Pageable pageable) {
        return ticketRepository.findAllByMember_Id(memberId, pageable).map(TicketListDTO::of);
    }
}
