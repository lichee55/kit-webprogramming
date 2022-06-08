package com.kit.kumovie.service.impl;

import com.kit.kumovie.domain.Member;
import com.kit.kumovie.domain.repository.MemberRepository;
import com.kit.kumovie.dto.SignInResultDTO;
import com.kit.kumovie.dto.SignUpDTO;
import com.kit.kumovie.dto.auth.SignInDTO;
import com.kit.kumovie.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomMemberDetailServiceImpl implements AuthService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findById(Long.valueOf(username)).orElseThrow(() -> new RuntimeException("토큰의 유저 정보가 유효하지 않습니다."));
    }

    @Override
    public SignInResultDTO getToken(SignInDTO signInDTO) {
        return null;
    }

    @Override
    public Boolean validateRefreshToken(String userPk, String refreshToken) {
        Member member = memberRepository.findById(Long.valueOf(userPk)).orElseThrow(() -> new RuntimeException("fail to find member"));
        return member.getRefreshToken().equals(refreshToken);
    }

    @Override
    @Transactional
    public void updateRefreshToken(String userPk, String refreshToken) {
        Member member = memberRepository.findById(Long.valueOf(userPk)).orElseThrow(() -> new RuntimeException("fail to update refresh token"));
        member.setRefreshToken(refreshToken);
    }

    @Override
    public void addUser(SignUpDTO signUpDTO) {
        Member member = signUpDTO.toEntity();
    }

}
