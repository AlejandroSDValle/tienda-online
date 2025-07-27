package com.tienda.online.repositories;

import com.tienda.online.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM VerificationToken t WHERE t.expiresAt < :now")
    void deleteAllByExpiresAtBefore(@Param("now") LocalDateTime now);
}
