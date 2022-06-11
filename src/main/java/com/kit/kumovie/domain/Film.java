package com.kit.kumovie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "films")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@NamedEntityGraphs(
        value = {
                @NamedEntityGraph(
                        name = "Film.withScreenings",
                        attributeNodes = @NamedAttributeNode(value = "screenings", subgraph = "Screening.withTickets"),
                        subgraphs = {
                                @NamedSubgraph(name = "Screening.withTickets", attributeNodes = @NamedAttributeNode(value = "tickets"))
                        }
                )
        }
)

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

    @Column(name = "price", nullable = false, precision = 6, scale = 1)
    private BigDecimal price;

    @Column(name = "seat_count", nullable = false)
    private Integer seatCount; // 현재 발권되었고 아직 상영하지 않은 예매 수

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "genre", nullable = false)
    private String genre;

    @OneToMany(mappedBy = "film", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Screening> screenings;

}