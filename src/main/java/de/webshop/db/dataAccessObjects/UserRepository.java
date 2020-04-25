package de.webshop.db.dataAccessObjects;

import de.webshop.entitites.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User getUserByEmail(String email);
}
