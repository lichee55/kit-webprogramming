package com.kit.kumovie.controller;

import com.kit.kumovie.common.ResponseForm;
import com.kit.kumovie.dto.ScreeningListDTO;
import com.kit.kumovie.service.ScreeningService;
import com.kit.kumovie.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RestController
@Slf4j
public class TicketController {

    private final TicketService ticketService;
    private final ScreeningService screeningService;

    @GetMapping("/api/screening")
    public ResponseForm<Object> getScreeningList(@RequestParam Map<String, String> params) {
        try {
            List<ScreeningListDTO> filmId = screeningService.getScreeningList(params.get("filmId"));
            return ResponseForm.builder().status("success").message("get screening list by filmId success").content(filmId).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseForm.builder().status("fail").message(e.getMessage()).content(Boolean.FALSE).build();
        }
    }

    @GetMapping("/api/screening/{screeningId}")
    public ResponseForm<Object> getScreeningDetail(@PathVariable Long screeningId) {
        try {
            return ResponseForm.builder().status("success").message("get screening detail success").content(screeningService.getScreeningDetail(screeningId)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseForm.builder().status("fail").message(e.getMessage()).content(Boolean.FALSE).build();
        }
    }
}
