package com.kit.kumovie.service.impl;

import com.kit.kumovie.common.Common;
import com.kit.kumovie.domain.DiscountType;
import com.kit.kumovie.domain.Member;
import com.kit.kumovie.domain.Screening;
import com.kit.kumovie.domain.Ticket;
import com.kit.kumovie.domain.repository.MemberRepository;
import com.kit.kumovie.domain.repository.ScreeningRepository;
import com.kit.kumovie.domain.repository.TicketRepository;
import com.kit.kumovie.dto.BuyTicketDTO;
import com.kit.kumovie.dto.TicketListDTO;
import com.kit.kumovie.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Override
    @Transactional
    public void buyTicket(BuyTicketDTO buyTicketDTO) {

        Member userContext = Common.getUserContext();
        Screening screening = screeningRepository.findById(buyTicketDTO.getScreeningId()).orElseThrow(() -> new RuntimeException("상영 정보가 유효하지 않습니다."));
        Integer rowCount = screening.getTheater().getRowCount();
        Integer colCount = screening.getTheater().getColCount();

        BigDecimal price = screening.getScreeningPrice();
        if (screening.getDiscountType().equals(DiscountType.AMOUNT)) {
            price = price.subtract(screening.getDiscountValue());
        } else if (screening.getDiscountType().equals(DiscountType.RATE)) {
            price = price.subtract(price.multiply(screening.getDiscountValue()).divide(BigDecimal.valueOf(100), 0, RoundingMode.FLOOR));
        }

        BigDecimal ticketPrice = price.multiply(BigDecimal.valueOf(buyTicketDTO.getSeatCount()));

        Ticket ticket = BuyTicketDTO.toEntity(buyTicketDTO);
        ticket.setScreening(screening);
        ticket.setMember(memberRepository.findById(userContext.getId()).orElseThrow(() -> new RuntimeException("회원 정보가 유효하지 않습니다.")));
        ticket.setPrice(ticketPrice);

        List<String> seatStatus = Arrays.stream(screening.getSeatStatus().split(",")).collect(Collectors.toList());
        for (String seat : buyTicketDTO.getSeatList()) {
            String[] split = seat.split(",");
            seatStatus.set(Integer.parseInt(split[0]) * colCount + Integer.parseInt(split[1]), "1");
        }
        String seatStatusStr = String.join(",", seatStatus);
        screening.setSeatStatus(seatStatusStr);
        screening.setRestSeatCount(screening.getRestSeatCount() - buyTicketDTO.getSeatCount());

        ticketRepository.save(ticket);
        screeningRepository.save(screening);
    }

    @Override
    public Page<TicketListDTO> memberTicketList(Long memberId, Pageable pageable) {
        return ticketRepository.findAllByMember_Id(memberId, pageable).map(TicketListDTO::of);
    }
}
