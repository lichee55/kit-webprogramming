package com.kit.kumovie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "content", length = 200, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}
