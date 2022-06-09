package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {

    Page<Film> findAllByActorContainsIgnoreCase(String actor, Pageable pageable);

    Page<Film> findAllByTitleContainsIgnoreCase(String title, Pageable pageable);

}
