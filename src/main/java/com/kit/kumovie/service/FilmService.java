package com.kit.kumovie.service;

import com.kit.kumovie.dto.FilmDetailDTO;
import com.kit.kumovie.dto.FilmListDTO;
import com.kit.kumovie.dto.FilmStatisticDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilmService {

    Page<FilmListDTO> getFilmList(Pageable pageable);

    Page<FilmListDTO> getFilmListByActor(String actor, Pageable pageable);

    Page<FilmListDTO> getFilmListByTitle(String title, Pageable pageable);

    Page<FilmListDTO> getNowFilmList(Pageable pageable);

    FilmDetailDTO getFilmDetail(Long id);

    FilmStatisticDTO getFilmStatistic(Long filmId);

}
