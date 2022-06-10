package com.kit.kumovie.dto;

import com.kit.kumovie.domain.Member;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberInfoDTO {
    private String loginId;

    public static MemberInfoDTO of(Member member) {
        return MemberInfoDTO.builder()
                .loginId(member.getLoginId())
                .build();
    }
}
