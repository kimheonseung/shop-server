package com.devh.project.common.repository;

import com.devh.project.common.entity.Member;
import com.devh.project.common.entity.MemberToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberTokenRepository extends JpaRepository<MemberToken, Long> {
    Optional<MemberToken> findByMember(Member member);
    boolean existsByMember(Member member);
    void deleteByMember(Member member);
}
