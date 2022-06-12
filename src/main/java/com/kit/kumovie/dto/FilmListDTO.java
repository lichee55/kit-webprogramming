package com.kit.kumovie.dto;

import com.kit.kumovie.domain.Film;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Data
@Builder
public class FilmListDTO {
    private Long id;
    private String title;
    private LocalDate releaseDate;
    private BigDecimal rating;
    private String director;
    private String actor;
    private String thumbnail;
    private String grade;
    private String genre;
    private Integer runningTime;

    private BigDecimal ticketRate;

    public static FilmListDTO of(Film film) {
        // ticketRate 는 내부에서 계산하지 못하므로 외부에서 계산해야 한다.
        return FilmListDTO.builder()
                .id(film.getId())
                .title(film.getTitle())
                .releaseDate(film.getReleaseDate())
                .rating(film.getCommentCount() != 0 ? BigDecimal.valueOf(film.getSumRating()).divide(BigDecimal.valueOf(film.getCommentCount()), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO)
                .director(film.getDirector())
                .actor(film.getActor())
                .grade(film.getGrade())
                .genre(film.getGenre())
                .thumbnail(film.getThumbnail())
                .ticketRate(new BigDecimal(0))
                .runningTime(film.getMovieDuration())
                .build();
    }
}