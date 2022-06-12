package com.kit.kumovie.controller;

import com.kit.kumovie.common.ResponseForm;
import com.kit.kumovie.dto.BuyTicketDTO;
import com.kit.kumovie.dto.ScreeningDetailDTO;
import com.kit.kumovie.dto.ScreeningListDTO;
import com.kit.kumovie.service.ScreeningService;
import com.kit.kumovie.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@Slf4j
public class TicketController {

    private final TicketService ticketService;
    private final ScreeningService screeningService;

    @Operation(summary = "선택한 영화의 상영 정보 리스트 조회", description = "상영 정보 리스트 조회")
    @Parameters(value = {@Parameter(name = "filmId", description = "영화 PK", required = true, in = ParameterIn.QUERY)})
    @GetMapping("/api/screening")
    public ResponseForm<List<ScreeningListDTO>> getScreeningList(
            @RequestParam Long filmId) {
        try {
            List<ScreeningListDTO> getScreenByFilmId = screeningService.getScreeningList(filmId);
            return new ResponseForm<>("success", "상영 정보 리스트 조회 성공", getScreenByFilmId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }

    @Operation(summary = "선택한 상영의 상세정보 조회", description = "상영 정보 상세 조회")
    @Parameters(value = {@Parameter(name = "screeningId", description = "상영정보 PK", required = true, in = ParameterIn.PATH)})
    @GetMapping("/api/screening/{screeningId}")
    public ResponseForm<ScreeningDetailDTO> getScreeningDetail(@PathVariable Long screeningId) {
        try {
            ScreeningDetailDTO screeningDetail = screeningService.getScreeningDetail(screeningId);
            return new ResponseForm<>("success", "상영 정보 상세 조회 성공", screeningDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }

    @Operation(summary = "극장의 상영 영화 조회", description = "극장의 상영 영화 조회")
    @GetMapping("/api/screening/cinema/{cinemaId}")
    public ResponseForm<List<ScreeningListDTO>> getScreeningListByTheaterId(@PathVariable Long cinemaId) {
        try {
            List<ScreeningListDTO> getScreenByTheaterId = screeningService.getScreeningListByTheaterId(cinemaId);
            return new ResponseForm<>("success", "극장의 상영 영화 조회 성공", getScreenByTheaterId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }

    @Operation(summary = "예매", description = "예매")
    @PostMapping("/api/ticket")
    public ResponseForm<Boolean> buyTicket(@RequestBody BuyTicketDTO buyTicketDTO) {
        try {
            log.info("buyTicketDTO: {}", buyTicketDTO);
            ticketService.buyTicket(buyTicketDTO);
            return new ResponseForm<>("success", "예매 성공", Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }
}
