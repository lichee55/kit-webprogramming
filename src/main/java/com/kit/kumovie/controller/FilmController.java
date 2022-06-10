package com.kit.kumovie.controller;

import com.kit.kumovie.common.ResponseForm;
import com.kit.kumovie.dto.FilmListDTO;
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
        ResponseForm<Page<FilmListDTO>> responseForm = new ResponseForm<>();
        try {
            if (title != null) {
                responseForm.setData(filmService.getFilmListByTitle(pageable, title));
            }
            if (actor != null) {
                responseForm.setData(filmService.getFilmListByActor(pageable, actor));
            }
            if (title == null && actor == null) {
                responseForm.setData(filmService.getFilmList(pageable));
            }
            responseForm.setMessage("전체 영화 리스트 조회 성공");
            responseForm.setStatus("success");
            return responseForm;
        } catch (Exception e) {
            e.printStackTrace();
            responseForm.setMessage(e.getMessage());
            responseForm.setStatus("fail");
            return responseForm;
        }
    }

    @Operation(summary = "영화 상세 조회", description = "영화 상세 조회")
    @GetMapping("/api/films/{filmId}")
    public ResponseForm<FilmDetailDTO> getFilmDetail(@PathVariable Long filmId) {
        ResponseForm<FilmDetailDTO> responseForm = new ResponseForm<>();
        try {
            responseForm.setData(filmService.getFilmDetail(filmId));
            responseForm.setMessage("영화 상세 조회 성공");
            responseForm.setStatus("success");
            return responseForm;
        } catch (Exception e) {
            e.printStackTrace();
            responseForm.setMessage(e.getMessage());
            responseForm.setStatus("fail");
            return responseForm;
        }
    }
}