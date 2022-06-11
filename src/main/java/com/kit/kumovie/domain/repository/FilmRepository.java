package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long>, CustomFilmRepository {

    List<Film> findAllByActorContainsIgnoreCase(String actor);

    List<Film> findAllByTitleContainsIgnoreCase(String title);

    @EntityGraph(value = "Film.withScreenings")
    @Query("select f from Film f where f.id = :id")
    Optional<Film> findByIdWithNamedEntityGraph(Long id);

    @Query("select distinct f from Film f, Screening s where f.id = s.film.id and s.startTime >= :startTime order by f.releaseDate desc ")
    List<Film> findAllByStartTimeAfter(LocalDateTime startTime);

    @Query("select f from Film f order by f.releaseDate desc")
    List<Film> findAllByReleaseDateDesc();


}
