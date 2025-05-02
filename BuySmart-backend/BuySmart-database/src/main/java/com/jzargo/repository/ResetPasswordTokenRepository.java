package com.jzargo.repository;

import com.jzargo.entity.ResetPasswordToken;
import com.jzargo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
    Optional<ResetPasswordToken> findByToken(String token);
    Optional<ResetPasswordToken> findByUserId(Long id);
    void deleteByUserId(Long userId);
    void deleteByToken(String token);
}
