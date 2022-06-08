package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
