package com.kit.kumovie.service.impl;

import com.kit.kumovie.domain.repository.FilmRepository;
import com.kit.kumovie.dto.FilmListDTO;
import com.kit.kumovie.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    @Override
    public Page<FilmListDTO> getFilmList(Pageable pageable) {
        return null;
    }
}
