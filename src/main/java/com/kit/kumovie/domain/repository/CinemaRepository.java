package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
