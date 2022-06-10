package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long>, CustomFilmRepository {

    Page<Film> findAllByActorContainsIgnoreCase(String actor, Pageable pageable);

    Page<Film> findAllByTitleContainsIgnoreCase(String title, Pageable pageable);

    @EntityGraph(value = "Film.withScreenings")
    @Query("select f from Film f where f.id = :id")
    Optional<Film> findByIdWithNamedEntityGraph(Long id);

}
