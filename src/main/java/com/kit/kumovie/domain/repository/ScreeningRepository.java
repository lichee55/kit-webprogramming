package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    List<Screening> findAllByFilm_IdAndStartTimeBetween(Long film_id, LocalDateTime startTime, LocalDateTime startTime2);

    List<Screening> findAllByTheater_Cinema_IdAndStartTimeBetween(Long cinemaId, LocalDateTime startTime, LocalDateTime startTime2);

    List<Screening> findAllByStartTimeBetweenOrderByStartTime(LocalDateTime startTime, LocalDateTime startTime2);

}
