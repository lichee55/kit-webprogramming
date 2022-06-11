package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    List<Screening> findAllByFilm_IdAndStartTimeBetween(Long film_id, LocalDateTime startTime, LocalDateTime startTime2);

}
