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
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RestController
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @GetMapping("/api/films")
    public ResponseForm<Object> getFilmList(Pageable pageable) {
        try {
            Page<FilmListDTO> films = filmService.getFilmList(pageable);
            return ResponseForm.builder().status("success").message("get film list success").content(films).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseForm.builder().status("fail").message(e.getMessage()).content(Boolean.FALSE).build();
        }
    }
}