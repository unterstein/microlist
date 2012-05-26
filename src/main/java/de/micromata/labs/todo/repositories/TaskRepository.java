package de.micromata.labs.todo.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.micromata.labs.todo.model.Task;
import de.micromata.labs.todo.model.User;

/**
 * 
 * @author <a href="mailto:j.unterstein@micromata.de">Johannes Unterstein</a>
 * 
 */
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByUser(User user);
}
