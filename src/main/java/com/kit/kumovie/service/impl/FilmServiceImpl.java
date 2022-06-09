package com.kit.kumovie.service.impl;

import com.kit.kumovie.domain.Film;
import com.kit.kumovie.domain.repository.FilmRepository;
import com.kit.kumovie.domain.repository.TicketRepository;
import com.kit.kumovie.dto.FilmListDTO;
import com.kit.kumovie.service.FilmDetailDTO;
import com.kit.kumovie.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
        LocalDateTime now = LocalDateTime.now();
        return ticketRepository.getTotalTicketCount(now.toLocalDate(), now.toLocalTime());
    }
}
