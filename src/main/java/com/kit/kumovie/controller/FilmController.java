package com.kit.kumovie.controller;

import com.kit.kumovie.common.ResponseForm;
import com.kit.kumovie.dto.FilmListDTO;
import com.kit.kumovie.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RestController
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @GetMapping("/api/films")
    public ResponseForm<Object> getFilmList(@RequestParam Map<String, String> params, Pageable pageable) {
        try {
            if (params.containsKey("actor")) {
                return ResponseForm.builder().status("success").message("get film list by actor success").content(filmService.getFilmListByActor(pageable, params.get("actor"))).build();
            }
            if (params.containsKey("title")) {
                return ResponseForm.builder().status("success").message("get film list by title success").content(filmService.getFilmListByTitle(pageable, params.get("title"))).build();
            }
            Page<FilmListDTO> films = filmService.getFilmList(pageable);
            return ResponseForm.builder().status("success").message("get film list success").content(films).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseForm.builder().status("fail").message(e.getMessage()).content(Boolean.FALSE).build();
        }
    }

    @GetMapping("/api/films/{filmId}")
    public ResponseForm<Object> getFilmDetail(@PathVariable Long filmId, Pageable pageable) {
        try {
            return ResponseForm.builder().status("success").message("get film detail success").content(filmService.getFilmDetail(filmId)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseForm.builder().status("fail").message(e.getMessage()).content(Boolean.FALSE).build();
        }
    }
}