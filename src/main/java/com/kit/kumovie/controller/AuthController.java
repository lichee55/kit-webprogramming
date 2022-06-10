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
        ResponseForm<SignInResultDTO> responseForm = new ResponseForm<>();
        try {
            SignInResultDTO tokens = authService.getToken(signInDTO);
            responseForm.setData(tokens);
            responseForm.setMessage("로그인 성공");
            responseForm.setStatus("success");
            return responseForm;
        } catch (Exception e) {
            e.printStackTrace();
            responseForm.setMessage(e.getMessage());
            responseForm.setStatus("fail");
            return responseForm;
        }
    }

    @Operation(summary = "회원가입", description = "회원가입")
    @PostMapping("/api/signup")
    public ResponseForm<Boolean> signUp(@RequestBody SignUpDTO signUpDTO) {
        ResponseForm<Boolean> responseForm = new ResponseForm<>();
        try {
            authService.addUser(signUpDTO);
            responseForm.setData(Boolean.TRUE);
            responseForm.setMessage("회원가입 성공");
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
