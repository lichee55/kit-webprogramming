package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
