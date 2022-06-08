package com.kit.kumovie.service;

import com.kit.kumovie.dto.SignInResultDTO;
import com.kit.kumovie.dto.SignUpDTO;
import com.kit.kumovie.dto.auth.SignInDTO;

public interface AuthService {
    SignInResultDTO getToken(SignInDTO signInDTO);

    void addUser(SignUpDTO signUpDTO);


}
