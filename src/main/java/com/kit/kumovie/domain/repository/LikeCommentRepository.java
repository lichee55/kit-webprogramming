package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
}
