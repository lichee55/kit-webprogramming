package com.kit.kumovie.service;

import com.kit.kumovie.dto.FilmListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilmService {

    Page<FilmListDTO> getFilmList(Pageable pageable);
}
