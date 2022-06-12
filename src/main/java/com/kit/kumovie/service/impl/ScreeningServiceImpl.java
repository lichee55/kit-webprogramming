package com.kit.kumovie.service.impl;

import com.kit.kumovie.domain.Film;
import com.kit.kumovie.domain.Screening;
import com.kit.kumovie.domain.repository.ScreeningRepository;
import com.kit.kumovie.dto.FilmListDTO;
import com.kit.kumovie.dto.ScreeningDetailDTO;
import com.kit.kumovie.dto.ScreeningListDTO;
import com.kit.kumovie.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;

    @Override
    public List<ScreeningListDTO> getScreeningList(Long filmId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime after = now.plusDays(2).withHour(0).withMinute(0).withSecond(0);
        List<Screening> findScreeningListByFilmId = screeningRepository.findAllByFilm_IdAndStartTimeBetween(filmId, now, after);
        return findScreeningListByFilmId.stream().map(ScreeningListDTO::of).collect(Collectors.toList());
    }

    @Override
    public List<ScreeningListDTO> getScreeningListByTheaterId(Long theaterId) {

        LocalDateTime now = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime after = now.plusDays(2).withHour(0).withMinute(0).withSecond(0);
        List<Screening> findScreeningListByTheaterId = screeningRepository.findAllByTheater_Cinema_IdAndStartTimeBetween(theaterId, now, after);
        return findScreeningListByTheaterId.stream().map(ScreeningListDTO::of).collect(Collectors.toList());
    }

    @Override
    public ScreeningDetailDTO getScreeningDetail(Long screeningId) {
        Screening findById = screeningRepository.findById(screeningId).orElseThrow(() -> new RuntimeException("상영 정보가 유효하지 않습니다."));
        return ScreeningDetailDTO.of(findById);
    }

}