package com.kit.kumovie.controller;

import com.kit.kumovie.common.ResponseForm;
import com.kit.kumovie.dto.SignInResultDTO;
import com.kit.kumovie.dto.SignUpDTO;
import com.kit.kumovie.dto.auth.SignInDTO;
import com.kit.kumovie.service.AuthService;
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

    @GetMapping("/api/signin")
    public ResponseForm<Object> signIn(@RequestBody SignInDTO signInDTO) {
        SignInResultDTO tokens = authService.getToken(signInDTO);
        return ResponseForm.builder().status("success").message("sign in success").content(tokens).build();
    }

    @PostMapping("/api/signup")
    public ResponseForm<Object> signUp(@RequestBody SignUpDTO signUpDTO) {
        try {
            log.info("signUp : {}", signUpDTO);
            authService.addUser(signUpDTO);
            return ResponseForm.builder().status("success").message("sign up success").content(Boolean.TRUE).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseForm.builder().status("fail").message(e.getMessage()).content(Boolean.FALSE).build();
        }
    }
}
