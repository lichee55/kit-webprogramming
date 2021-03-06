package com.kit.kumovie.dto;

import com.kit.kumovie.domain.Film;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;

@Data
@Builder
public class FilmDetailDTO {

    private Long id;
    private String title;
    private String description;
    private String thumbnail;
    private LocalDate releaseDate;
    private Integer movieDuration;
    private BigDecimal rating;
    private String director;
    private String grade;
    private String genre;
    private String actor;
    private BigDecimal ticketRate;

    public static FilmDetailDTO of(Film film) {
        // ticketRate 는 내부에서 계산하지 못하므로 외부에서 계산해야 한다.
        return FilmDetailDTO.builder()
                .id(film.getId())
                .title(film.getTitle())
                .description(film.getDescription())
                .thumbnail(film.getThumbnail())
                .releaseDate(film.getReleaseDate())
                .movieDuration(film.getMovieDuration())
                .rating(film.getCommentCount() == 0 ? BigDecimal.ZERO : BigDecimal.valueOf(film.getSumRating()).divide(BigDecimal.valueOf(film.getCommentCount()), 2, RoundingMode.HALF_UP))
                .director(film.getDirector())
                .actor(film.getActor())
                .build();
    }
}
