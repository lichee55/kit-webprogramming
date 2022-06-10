package com.kit.kumovie.controller;

import com.kit.kumovie.common.Common;
import com.kit.kumovie.common.ResponseForm;
import com.kit.kumovie.domain.Member;
import com.kit.kumovie.dto.MemberInfoDTO;
import com.kit.kumovie.dto.TicketListDTO;
import com.kit.kumovie.service.MemberService;
import com.kit.kumovie.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
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
public class MemberController {

    private final MemberService memberService;
    private final TicketService ticketService;

    @Operation(summary = "내 예매 내역", description = "내 예매 내역")
    @GetMapping("/api/myticket")
    public ResponseForm<Page<TicketListDTO>> getMyTicket(Pageable pageable) {
        try {
            Member userContext = Common.getUserContext();
            Page<TicketListDTO> ticketListDTO = ticketService.memberTicketList(userContext.getId(), pageable);
            return new ResponseForm<>("success", "내 예매 내역 조회 성공", ticketListDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }

    @Operation(summary = "내 정보 조회", description = "내 정보 조회")
    @GetMapping("/api/myinfo")
    public ResponseForm<MemberInfoDTO> getMyInfo() {
        try {
            Member userContext = Common.getUserContext();
            MemberInfoDTO memberInfoDTO = memberService.getMemberInfo(userContext.getId());
            return new ResponseForm<>("success", "내 정보 조회 성공", memberInfoDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }
}
