package fr.cttt.arosaje.repository;

import fr.cttt.arosaje.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordTokenRepository extends JpaRepository<ForgotPasswordToken, Long> {
    Optional<ForgotPasswordToken> findByToken(String token);
    void deleteAllByUserId(Long userId);
}
