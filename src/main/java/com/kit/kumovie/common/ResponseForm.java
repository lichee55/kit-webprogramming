package com.kit.kumovie.common;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseForm<T> {
    private String status;
    private String message;
    private T data;
}