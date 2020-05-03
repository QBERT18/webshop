package de.webshop.db.dataAccessObjects;

import de.webshop.entities.VerificationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<VerificationToken, Long> {

    Optional<VerificationToken> findVerificationTokenByToken(final String token);
}
