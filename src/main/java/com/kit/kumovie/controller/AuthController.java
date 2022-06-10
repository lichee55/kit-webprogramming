package com.kit.kumovie.controller;

import com.kit.kumovie.common.ResponseForm;
import com.kit.kumovie.dto.SignInResultDTO;
import com.kit.kumovie.dto.SignUpDTO;
import com.kit.kumovie.dto.auth.SignInDTO;
import com.kit.kumovie.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = "로그인")
    @GetMapping("/api/signin")
    public ResponseForm<SignInResultDTO> signIn(@RequestBody SignInDTO signInDTO) {
        try {
            SignInResultDTO tokens = authService.getToken(signInDTO);
            return new ResponseForm<>("success", "로그인 성공", tokens);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>(e.getMessage(), "로그인 실패", null);
        }
    }

    @Operation(summary = "회원가입", description = "회원가입")
    @PostMapping("/api/signup")
    public ResponseForm<Boolean> signUp(@RequestBody SignUpDTO signUpDTO) {
        try {
            authService.addUser(signUpDTO);
            return new ResponseForm<>("success", "회원가입 성공", Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseForm<>("fail", e.getMessage(), null);
        }
    }
}
