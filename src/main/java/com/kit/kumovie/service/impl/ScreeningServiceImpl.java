package com.kit.kumovie.service.impl;

import com.kit.kumovie.domain.Screening;
import com.kit.kumovie.domain.repository.ScreeningRepository;
import com.kit.kumovie.dto.ScreeningDetailDTO;
import com.kit.kumovie.dto.ScreeningListDTO;
import com.kit.kumovie.service.ScreeningService;
import lombok.RequiredArgsConstructor;
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
    public List<ScreeningListDTO> getScreeningList(String filmId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime after = now.plusDays(5);
        List<Screening> findScreeningListByFilmId = screeningRepository.findAllByFilm_IdAndStartTimeBetween(Long.valueOf(filmId), now, after);
        return findScreeningListByFilmId.stream().map(ScreeningListDTO::of).collect(Collectors.toList());
    }

    @Override
    public ScreeningDetailDTO getScreeningDetail(Long screeningId) {
        Screening findById = screeningRepository.findById(screeningId).orElseThrow(() -> new RuntimeException("상영 정보가 유효하지 않습니다."));
        return ScreeningDetailDTO.of(findById);
    }
}