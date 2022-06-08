package com.kit.kumovie.dto;

import lombok.Data;

@Data
public class SignInResultDTO {
    private String accessToken;
    private String refreshToken;
}
