package com.kit.kumovie.service;

import com.kit.kumovie.dto.FilmListDTO;
import com.kit.kumovie.dto.FilmStatisticDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilmService {

    Page<FilmListDTO> getFilmList(Pageable pageable);

    Page<FilmListDTO> getFilmListByActor(Pageable pageable, String actor);

    Page<FilmListDTO> getFilmListByTitle(Pageable pageable, String title);

    FilmDetailDTO getFilmDetail(Long id);

    FilmStatisticDTO getFilmStatistic(Long filmId);

}
