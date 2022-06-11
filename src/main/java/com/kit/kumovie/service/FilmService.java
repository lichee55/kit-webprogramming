package com.kit.kumovie.service;

import com.kit.kumovie.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilmService {

    Page<FilmListDTO> getFilmList(Pageable pageable);

    Page<FilmListDTO> getFilmListByActor(String actor, Pageable pageable);

    Page<FilmListDTO> getFilmListByTitle(String title, Pageable pageable);

    Page<FilmListDTO> getNowFilmList(Pageable pageable);

    Page<CommentDTO> getFilmComments(Long filmId, Pageable pageable);

    FilmDetailDTO getFilmDetail(Long id);

    FilmStatisticDTO getFilmStatistic(Long filmId);

    void writeFilmComment(Long filmId, WriteCommentDTO writeCommentDTO);

    void updateFilmComment(Long filmId, Long commentId, CommentDTO commentDTO);

    void deleteFilmComment(Long filmId, Long commentId);
}
