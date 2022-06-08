package com.kit.kumovie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "films")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Film extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Long id;

    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "movie_duration", nullable = false)
    private Integer movieDuration;

    @Column(name = "rating", nullable = false, precision = 3, scale = 1)
    private BigDecimal rating;

    @Column(name = "director", length = 15, nullable = true)
    private String director;

    @Column(name = "actor", length = 50, nullable = true)
    private String actor;

    @Column(name = "price", nullable = false, precision = 5, scale = 1)
    private BigDecimal price;

    @Column(name = "ticket_count", nullable = false)
    private Integer ticketCount;
}