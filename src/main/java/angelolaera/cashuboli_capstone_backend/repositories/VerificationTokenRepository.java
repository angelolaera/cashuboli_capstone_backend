package angelolaera.cashuboli_capstone_backend.repositories;

import angelolaera.cashuboli_capstone_backend.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}
