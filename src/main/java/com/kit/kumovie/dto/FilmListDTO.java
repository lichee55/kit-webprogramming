package com.kit.kumovie.dto;

import com.kit.kumovie.domain.Film;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
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

    private BigDecimal ticketRate;

    public static FilmListDTO of(Film film) {
        // ticketRate 는 내부에서 계산하지 못하므로 외부에서 계산해야 한다.
        return FilmListDTO.builder()
                .id(film.getId())
                .title(film.getTitle())
                .releaseDate(film.getReleaseDate())
                .rating(film.getRating())
                .director(film.getDirector())
                .actor(film.getActor())
                .thumbnail(film.getThumbnail())
                .ticketRate(new BigDecimal(0))
                .build();
    }
}