package com.kit.kumovie.service;

import com.kit.kumovie.dto.MemberInfoDTO;

public interface MemberService {

    MemberInfoDTO getMemberInfo(Long memberId);
}
