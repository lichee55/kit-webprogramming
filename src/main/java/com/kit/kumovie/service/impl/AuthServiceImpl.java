package com.kit.kumovie.service.impl;

import com.kit.kumovie.domain.repository.MemberRepository;
import com.kit.kumovie.dto.SignInResultDTO;
import com.kit.kumovie.dto.auth.SignInDTO;
import com.kit.kumovie.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;

    @Override
    public SignInResultDTO getToken(SignInDTO signInDTO) {
        return null;
    }
}
