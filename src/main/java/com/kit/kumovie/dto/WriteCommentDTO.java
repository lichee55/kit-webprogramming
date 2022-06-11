package com.kit.kumovie.dto;

import com.kit.kumovie.domain.Comment;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class WriteCommentDTO {
    private Integer rating;
    private String content;

    public static Comment toEntity(WriteCommentDTO writeCommentDTO) {
        return Comment.builder()
                .content(writeCommentDTO.getContent())
                .rating(writeCommentDTO.getRating())
                .build();
    }
}
