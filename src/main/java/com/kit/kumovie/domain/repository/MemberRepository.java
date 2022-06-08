package com.kit.kumovie.domain.repository;

import com.kit.kumovie.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
