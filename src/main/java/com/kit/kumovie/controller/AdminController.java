package com.kit.kumovie.controller;

import com.kit.kumovie.common.ResponseForm;
import com.kit.kumovie.dto.ApplyDiscountDTO;
import com.kit.kumovie.dto.ScreeningListDTO;
import com.kit.kumovie.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "관리자 상영내역 조회", description = "관리자 상영내역 조회")
    @GetMapping("/api/admin/movie/list")
    public ResponseForm<List<ScreeningListDTO>> movieList() {
        try {
            List<ScreeningListDTO> screeningList = adminService.getScreeningList();
            return new ResponseForm<>("success", "영화 목록 조회 성공", screeningList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }

    @Operation(summary = "상영에 할인 적용", description = "상영에 할인 적용")
    @PostMapping("/api/admin/movie/discount")
    public ResponseForm<Boolean> screeningDiscount(@RequestBody ApplyDiscountDTO applyDiscountDTO) {
        try {
            adminService.applyDiscount(applyDiscountDTO);
            return new ResponseForm<>("success", "할인 적용 성공", Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }

}
