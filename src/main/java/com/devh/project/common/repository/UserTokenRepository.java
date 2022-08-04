package com.devh.project.common.repository;

import com.devh.project.common.entity.User;
import com.devh.project.common.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByUser(User user);
    boolean existsByUser(User user);
    void deleteByUser(User user);
}
