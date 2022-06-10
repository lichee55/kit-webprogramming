package com.kit.kumovie.controller;

import com.kit.kumovie.common.ResponseForm;
import com.kit.kumovie.dto.FilmListDTO;
import com.kit.kumovie.dto.FilmStatisticDTO;
import com.kit.kumovie.service.FilmDetailDTO;
import com.kit.kumovie.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                Page<FilmListDTO> filmListByTitle = filmService.getFilmListByTitle(pageable, title);
                return new ResponseForm<>("success", "전체 영화 리스트 조회 성공", filmListByTitle);
            }
            if (actor != null) {
                Page<FilmListDTO> filmListByActor = filmService.getFilmListByActor(pageable, actor);
                return new ResponseForm<>("success", "전체 영화 리스트 조회 성공", filmListByActor);
            }
            Page<FilmListDTO> filmList = filmService.getFilmList(pageable);
            return new ResponseForm<>("success", "전체 영화 리스트 조회 성공", filmList);
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