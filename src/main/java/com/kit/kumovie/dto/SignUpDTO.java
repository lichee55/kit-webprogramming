package com.kit.kumovie.dto;

import com.kit.kumovie.domain.Member;
import com.kit.kumovie.domain.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpDTO {
    private String loginId;
    private String password;

    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .role(Role.ROLE_USER)
                .build();
    }
}
