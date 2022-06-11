package com.kit.kumovie.dto;

import com.kit.kumovie.domain.Gender;
import com.kit.kumovie.domain.Member;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberInfoDTO {
    private String loginId;
    private String name;
    private Integer age;
    private Gender gender;

    public static MemberInfoDTO of(Member member) {
        return MemberInfoDTO.builder()
                .loginId(member.getLoginId())
                .name(member.getName())
                .age(member.getAge())
                .gender(member.getGender())
                .build();
    }
}
