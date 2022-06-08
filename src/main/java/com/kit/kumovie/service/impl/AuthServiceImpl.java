package com.kit.kumovie.service.impl;

import com.kit.kumovie.config.jwt.JwtTokenProvider;
import com.kit.kumovie.domain.Member;
import com.kit.kumovie.domain.repository.MemberRepository;
import com.kit.kumovie.dto.SignInResultDTO;
import com.kit.kumovie.dto.SignUpDTO;
import com.kit.kumovie.dto.auth.SignInDTO;
import com.kit.kumovie.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public SignInResultDTO getToken(SignInDTO signInDTO) {
        Member member = memberRepository.findByLoginId(signInDTO.getLoginId()).orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));
        if (!passwordEncoder.matches(signInDTO.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        String token = jwtTokenProvider.createToken(String.valueOf(member.getId()), member.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(member.getId()), member.getRole());
        return SignInResultDTO.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public void addUser(SignUpDTO signUpDTO) {
        if (memberRepository.findByLoginId(signUpDTO.getLoginId()).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signUpDTO.getPassword());
        signUpDTO.setPassword(encodedPassword);
        Member entity = signUpDTO.toEntity();
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        memberRepository.save(entity);
    }
}
