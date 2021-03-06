package com.kit.kumovie.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResultDTO {
    private String accessToken;
    private String refreshToken;
}
