package com.italo.TaskBrother.models.repository;

import com.italo.TaskBrother.models.entities.VerificationCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCodeModel, UUID> {
    Optional<VerificationCodeModel> findByEmailAndCode(String email, String code);
    Optional<VerificationCodeModel> findByEmail(String email);
    
    @Modifying
    @Query("DELETE FROM VerificationCodeModel v WHERE v.expiresAt < :now")
    void deleteExpiredCodes(LocalDateTime now);
}