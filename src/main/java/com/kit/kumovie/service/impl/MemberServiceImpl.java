package com.kit.kumovie.service.impl;

import com.kit.kumovie.domain.repository.MemberRepository;
import com.kit.kumovie.dto.MemberInfoDTO;
import com.kit.kumovie.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberInfoDTO getMemberInfo(Long memberId) {
        return memberRepository.findById(memberId).map(MemberInfoDTO::of).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
