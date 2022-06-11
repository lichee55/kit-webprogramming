package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    List<LikeComment> findAllByMember_IdOrderByIdDesc(Long memberId);
}
