package com.kit.kumovie.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseForm<T> {
    private String status;
    private String message;
    private T content;
}