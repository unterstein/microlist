package de.micromata.labs.todo.services;

import java.io.Serializable;
import java.util.List;

import de.micromata.labs.todo.exceptions.UserAlreadyExistsException;
import de.micromata.labs.todo.model.Project;
import de.micromata.labs.todo.model.Task;
import de.micromata.labs.todo.model.TaskDueStatus;
import de.micromata.labs.todo.model.User;

/**
 * 
 * @author <a href="mailto:j.unterstein@micromata.de">Johannes Unterstein</a>
 * 
 */
public interface ITodoService extends Serializable {

    public static final String ROLE_LOGGEDIN = "loggedin";

    // ----------------------------------
    // ----------------------------------
    // Users
    // ----------------------------------
    // ----------------------------------

    User signIn(String email, String password);

    User getUser(Long userId);

    User register(User user) throws UserAlreadyExistsException;

    // ----------------------------------
    // ----------------------------------
    // Projects
    // ----------------------------------
    // ----------------------------------

    List<Project> getProjects(User user);

    Project getProject(Long projectId);

    // ----------------------------------
    // ----------------------------------
    // Tasks
    // ----------------------------------
    // ----------------------------------
    List<Task> getTasks(User user);

    List<Task> getTasks(User user, TaskDueStatus status);

    void addTask(Task task);

    void addTask(Project project, Task task);

    void editTask(Task task);

    void setFinished(Task task, boolean finished);
}
