package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.Film;

import java.util.Optional;

public interface CustomFilmRepository {
    Optional<Film> findByIdWithEntityGraph(Long id);
}
