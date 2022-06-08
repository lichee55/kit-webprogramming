package com.kit.kumovie.service;

import com.kit.kumovie.dto.SignInResultDTO;
import com.kit.kumovie.dto.SignUpDTO;
import com.kit.kumovie.dto.auth.SignInDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    SignInResultDTO getToken(SignInDTO signInDTO);

    Boolean validateRefreshToken(String userPk, String refreshToken);

    void updateRefreshToken(String userPk, String refreshToken);

    void addUser(SignUpDTO signUpDTO);


}
