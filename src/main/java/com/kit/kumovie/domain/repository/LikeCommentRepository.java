package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    List<LikeComment> findAllByMember_IdOrderByIdDesc(Long memberId);

    Optional<LikeComment> findByMember_IdAndComment_Id(Long memberId, Long commentId);
}
