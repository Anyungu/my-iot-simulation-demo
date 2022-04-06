package com.anyungu.data.mapping.repos;

import com.anyungu.data.mapping.entities.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {
    Optional<PasswordReset> findByResetToken(UUID token);
}
