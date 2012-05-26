package de.micromata.labs.todo.repositories;

import org.springframework.data.repository.CrudRepository;

import de.micromata.labs.todo.model.User;

/**
 * 
 * @author <a href="mailto:j.unterstein@micromata.de">Johannes Unterstein</a>
 * 
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findOneByEmail(String email);
}
