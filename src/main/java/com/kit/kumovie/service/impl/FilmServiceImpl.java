package com.kit.kumovie.service.impl;

import com.kit.kumovie.domain.Film;
import com.kit.kumovie.domain.Gender;
import com.kit.kumovie.domain.Member;
import com.kit.kumovie.domain.repository.FilmRepository;
import com.kit.kumovie.domain.repository.TicketRepository;
import com.kit.kumovie.dto.FilmListDTO;
import com.kit.kumovie.dto.FilmStatisticDTO;
import com.kit.kumovie.service.FilmDetailDTO;
import com.kit.kumovie.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;
    private final TicketRepository ticketRepository;

    @Override
    public Page<FilmListDTO> getFilmList(Pageable pageable) {
        Page<Film> all = filmRepository.findAll(pageable);
        return toDTOPageInsertTicketRate(all);
    }

    @Override
    public Page<FilmListDTO> getFilmListByActor(Pageable pageable, String actor) {
        Page<Film> findByActor = filmRepository.findAllByActorContainsIgnoreCase(actor, pageable);
        return toDTOPageInsertTicketRate(findByActor);
    }

    @Override
    public Page<FilmListDTO> getFilmListByTitle(Pageable pageable, String title) {
        Page<Film> findByTitle = filmRepository.findAllByTitleContainsIgnoreCase(title, pageable);
        return toDTOPageInsertTicketRate(findByTitle);
    }

    @Override
    public FilmDetailDTO getFilmDetail(Long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영화입니다."));
        Long totalTicketCount = totalTicketCount();
        FilmDetailDTO of = FilmDetailDTO.of(film);
        BigDecimal ticketRate = BigDecimal.valueOf(film.getSeatCount()).divide(BigDecimal.valueOf(totalTicketCount), MathContext.DECIMAL32);
        of.setTicketRate(ticketRate);
        return of;
    }

    @Override
    public FilmStatisticDTO getFilmStatistic(Long filmId) {
//        Film film = filmRepository.findByIdWithEntityGraph(filmId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영화입니다."));
        Film film = filmRepository.findByIdWithNamedEntityGraph(filmId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영화입니다."));

        List<Member> ticketedMembers = new ArrayList<>();

        film.getScreenings().forEach(sc -> {
            sc.getTickets().forEach(t -> {
                ticketedMembers.add(t.getMember());
            });
        });

        long maleCount = 0L;
        long[] ageCount = new long[20];


        for (Member member : ticketedMembers) {
            if (member.getGender() == Gender.MALE) {
                maleCount++;
            }
            int i = member.getAge() / 10;
            ageCount[i]++;
        }
        int memberCount = ticketedMembers.size();
        BigDecimal maleRate = BigDecimal.valueOf(maleCount).divide(BigDecimal.valueOf(memberCount), MathContext.DECIMAL32);
        return FilmStatisticDTO.builder()
                .maleRate(maleRate.multiply(BigDecimal.valueOf(100)))
                .femaleRate(BigDecimal.ONE.subtract(maleRate).multiply(BigDecimal.valueOf(100)))
                .age10Rate(BigDecimal.valueOf(ageCount[1]).divide(BigDecimal.valueOf(memberCount), MathContext.DECIMAL32).multiply(BigDecimal.valueOf(100)))
                .age20Rate(BigDecimal.valueOf(ageCount[2]).divide(BigDecimal.valueOf(memberCount), MathContext.DECIMAL32).multiply(BigDecimal.valueOf(100)))
                .age30Rate(BigDecimal.valueOf(ageCount[3]).divide(BigDecimal.valueOf(memberCount), MathContext.DECIMAL32).multiply(BigDecimal.valueOf(100)))
                .age40Rate(BigDecimal.valueOf(ageCount[4]).divide(BigDecimal.valueOf(memberCount), MathContext.DECIMAL32).multiply(BigDecimal.valueOf(100)))
                .age50Rate(BigDecimal.valueOf(ageCount[5]).divide(BigDecimal.valueOf(memberCount), MathContext.DECIMAL32).multiply(BigDecimal.valueOf(100)))
                .build();
    }

    private Page<FilmListDTO> toDTOPageInsertTicketRate(Page<Film> filmPage) {
        Long totalTicketCount = totalTicketCount();
        if (totalTicketCount == 0) {
            return filmPage.map(FilmListDTO::of);
        }
        return filmPage.map(film -> {
            FilmListDTO of = FilmListDTO.of(film);
            BigDecimal ticketRate = BigDecimal.valueOf(film.getSeatCount()).divide(BigDecimal.valueOf(totalTicketCount), MathContext.DECIMAL32);
            of.setTicketRate(ticketRate);
            return of;
        });
    }

    private Long totalTicketCount() {
        return ticketRepository.getTotalTicketCount(LocalDateTime.now());
    }
}
