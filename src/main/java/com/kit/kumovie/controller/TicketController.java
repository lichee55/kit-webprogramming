package com.kit.kumovie.controller;

import com.kit.kumovie.common.ResponseForm;
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
        ResponseForm<List<ScreeningListDTO>> responseForm = new ResponseForm<>();
        try {
            List<ScreeningListDTO> getScreenByFilmId = screeningService.getScreeningList(filmId);
            responseForm.setData(getScreenByFilmId);
            responseForm.setMessage("상영 정보 조회 성공");
            responseForm.setStatus("success");
            return responseForm;
        } catch (Exception e) {
            e.printStackTrace();
            responseForm.setMessage(e.getMessage());
            responseForm.setStatus("fail");
            return responseForm;
        }
    }

    @Operation(summary = "선택한 상영의 상세정보 조회", description = "상영 정보 상세 조회")
    @Parameters(value = {@Parameter(name = "screeningId", description = "상영정보 PK", required = true, in = ParameterIn.PATH)})
    @GetMapping("/api/screening/{screeningId}")
    public ResponseForm<ScreeningDetailDTO> getScreeningDetail(@PathVariable Long screeningId) {
        ResponseForm<ScreeningDetailDTO> responseForm = new ResponseForm<>();
        try {
            ScreeningDetailDTO screeningDetail = screeningService.getScreeningDetail(screeningId);
            responseForm.setData(screeningDetail);
            responseForm.setMessage("상영 정보 조회 성공");
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
