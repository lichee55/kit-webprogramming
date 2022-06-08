package com.kit.kumovie.controller;

import com.kit.kumovie.common.ResponseForm;
import com.kit.kumovie.dto.SignInResultDTO;
import com.kit.kumovie.dto.auth.SignInDTO;
import com.kit.kumovie.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller(value = "/api")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signin")
    public ResponseForm<Object> signIn(@RequestBody SignInDTO signInDTO) {
        SignInResultDTO accessToken = authService.getToken(signInDTO);
        return ResponseForm.builder().status("success").message("sign in success").content(accessToken).build();
    }
}
