package de.webshop.db.dataAccessObjects;

import de.webshop.entities.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<VerificationToken, Long> {

    VerificationToken getTokenByEmail(String mail);
}
