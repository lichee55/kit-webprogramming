package com.kit.kumovie.controller;

import com.kit.kumovie.common.ResponseForm;
import com.kit.kumovie.dto.*;
import com.kit.kumovie.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RestController
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @Operation(summary = "전체 영화 리스트 조회", description = "전체 영화 리스트 조회")
    @GetMapping("/api/films")
    public ResponseForm<Page<FilmListDTO>> getFilmList(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String actor,
            Pageable pageable) {
        try {
            if (title != null) {
                Page<FilmListDTO> filmListByTitle = filmService.getFilmListByTitle(title, pageable);
                return new ResponseForm<>("success", "전체 영화 리스트 조회 성공", filmListByTitle);
            }
            if (actor != null) {
                Page<FilmListDTO> filmListByActor = filmService.getFilmListByActor(actor, pageable);
                return new ResponseForm<>("success", "전체 영화 리스트 조회 성공", filmListByActor);
            }
            Page<FilmListDTO> filmList = filmService.getFilmList(pageable);
            return new ResponseForm<>("success", "전체 영화 리스트 조회 성공", filmList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }

    @Operation(summary = "상영 영화 리스트 조회", description = "상영 영화 리스트 조회")
    @GetMapping("/api/films/now")
    public ResponseForm<Page<FilmListDTO>> getNowFilmList(Pageable pageable) {
        try {
            Page<FilmListDTO> filmList = filmService.getNowFilmList(pageable);
            return new ResponseForm<>("success", "상영 영화 리스트 조회 성공", filmList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }

    @Operation(summary = "영화 상세 조회", description = "영화 상세 조회")
    @GetMapping("/api/films/{filmId}")
    public ResponseForm<FilmDetailDTO> getFilmDetail(@PathVariable Long filmId) {
        try {
            FilmDetailDTO filmDetail = filmService.getFilmDetail(filmId);
            return new ResponseForm<>("success", "영화 상세 조회 성공", filmDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }

    @Operation(summary = "영화 댓글 조회", description = "영화 댓글 조회")
    @GetMapping("/api/films/{filmId}/comments")
    public ResponseForm<Page<CommentDTO>> getFilmComments(@PathVariable Long filmId, Pageable pageable) {
        try {
            Page<CommentDTO> filmComments = filmService.getFilmComments(filmId, pageable);
            return new ResponseForm<>("success", "영화 댓글 조회 성공", filmComments);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }

    @Operation(summary = "영화 댓글 좋아요", description = "영화 댓글 좋아요")
    @PostMapping("/api/films/{filmId}/comments/{commentId}/like")
    public ResponseForm<Boolean> likeComment(@PathVariable Long filmId, @PathVariable Long commentId) {
        try {
            filmService.likeComment(filmId, commentId);
            return new ResponseForm<>("success", "영화 댓글 좋아요 성공", Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }

    @Operation(summary = "영화 댓글 작성", description = "영화 댓글 작성")
    @PostMapping("/api/films/{filmId}/comments/write")
    public ResponseForm<Boolean> writeFilmComment(@PathVariable Long filmId, @RequestBody WriteCommentDTO writeCommentDTO) {
        try {
            filmService.writeFilmComment(filmId, writeCommentDTO);
            return new ResponseForm<>("success", "영화 댓글 작성 성공", Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }

    @Operation(summary = "영화 댓글 수정", description = "영화 댓글 수정")
    @PutMapping("/api/films/{filmId}/comments/{commentId}")
    public ResponseForm<Boolean> updateFilmComment(@PathVariable Long filmId, @PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
        try {
            filmService.updateFilmComment(filmId, commentId, commentDTO);
            return new ResponseForm<>("success", "영화 댓글 수정 성공", Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }

    @Operation(summary = "영화 댓글 삭제", description = "영화 댓글 삭제")
    @DeleteMapping("/api/films/{filmId}/comments/{commentId}")
    public ResponseForm<Boolean> deleteFilmComment(@PathVariable Long filmId, @PathVariable Long commentId) {
        try {
            filmService.deleteFilmComment(filmId, commentId);
            return new ResponseForm<>("success", "영화 댓글 삭제 성공", Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }

    @Operation(summary = "영화 통계 조회", description = "영화 통계 조회")
    @GetMapping("/api/films/statistics")
    public ResponseForm<FilmStatisticDTO> getFilmStatistics(@RequestParam Long filmId) {
        try {
            FilmStatisticDTO filmStatistic = filmService.getFilmStatistic(filmId);
            return new ResponseForm<>("success", "영화 통계 조회 성공", filmStatistic);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }
}