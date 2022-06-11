package com.kit.kumovie.dto;

import com.kit.kumovie.domain.Comment;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentDTO {
    private Long id;
    private String content;
    private String memberName;
    private Integer rating;
    private Integer likeCount;

    private Boolean isMyComment;
    private Boolean isLiked;

    public static CommentDTO of(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .memberName(comment.getMember().getName())
                .rating(comment.getRating())
                .likeCount(comment.getLikeCount())
                .build();
    }
}
