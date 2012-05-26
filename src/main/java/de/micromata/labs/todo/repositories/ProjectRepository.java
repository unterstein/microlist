package de.micromata.labs.todo.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.micromata.labs.todo.model.Project;
import de.micromata.labs.todo.model.User;

/**
 * 
 * @author <a href="mailto:j.unterstein@micromata.de">Johannes Unterstein</a>
 * 
 */
public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findByUser(User user);
}
