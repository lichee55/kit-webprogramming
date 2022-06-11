package com.kit.kumovie.service;

import com.kit.kumovie.domain.Film;
import com.kit.kumovie.dto.FilmListDTO;
import com.kit.kumovie.dto.ScreeningDetailDTO;
import com.kit.kumovie.dto.ScreeningListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScreeningService {

    List<ScreeningListDTO> getScreeningList(Long filmId);

    ScreeningDetailDTO getScreeningDetail(Long screeningId);

}
