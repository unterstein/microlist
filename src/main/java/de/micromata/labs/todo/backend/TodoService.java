package de.micromata.labs.todo.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.micromata.labs.todo.exceptions.UserAlreadyExistsException;
import de.micromata.labs.todo.model.Project;
import de.micromata.labs.todo.model.Task;
import de.micromata.labs.todo.model.TaskDueStatus;
import de.micromata.labs.todo.model.User;
import de.micromata.labs.todo.repositories.ProjectRepository;
import de.micromata.labs.todo.repositories.TaskRepository;
import de.micromata.labs.todo.repositories.UserRepository;
import de.micromata.labs.todo.services.ITodoService;

/**
 * Backend implementation, more or less a {@link Repository} wrapper.
 * 
 * @author <a href="mailto:j.unterstein@micromata.de">Johannes Unterstein</a>
 * 
 */
public class TodoService implements ITodoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public User signIn(String email, String password) {
        User resultFromDb = this.userRepository.findOneByEmail(email);
        User result = null;
        if (resultFromDb != null) {
            if (resultFromDb.getPassword().equals(password)) {
                result = resultFromDb;
            }
        }
        return result;
    }

    @Override
    public User getUser(Long userId) {
        return this.userRepository.findOne(userId);
    }

    @Transactional
    @Override
    public User register(User user) throws UserAlreadyExistsException {
        if (this.userRepository.findOneByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException();
        }
        return this.userRepository.save(user);
    }

    @Override
    public List<Project> getProjects(User user) {
        return this.projectRepository.findByUser(user);
    }

    @Override
    public Project getProject(Long projectId) {
        return this.projectRepository.findOne(projectId);
    }

    @Override
    public void addProject(User user, Project project) {
        if (user == null || project == null) {
            return;
        }
        User userFromDb = this.userRepository.findOne(user.getId());
        project.setUser(userFromDb);
        this.projectRepository.save(project);
    }

    @Override
    public void editProject(Project project) {
        if (project == null) {
            return;
        }
        this.projectRepository.save(project);
    }

    @Override
    public List<Task> getTasks(User user) {
        return this.taskRepository.findByUser(user);
    }

    @Override
    public List<Task> getTasks(User user, TaskDueStatus status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addTask(Task task) {
        this.taskRepository.save(task);
    }

    @Override
    public void addTask(Project project, Task task) {
        if (project == null || task == null)
            return;
        Project projectFromDb = this.projectRepository.findOne(project.getId());
        Task taskFromDb = this.taskRepository.findOne(task.getId());
        taskFromDb.setProject(projectFromDb);
    }

    @Override
    public void editTask(Task task) {
        if (task == null)
            return;
        this.taskRepository.save(task);
    }

    @Override
    public void setFinished(Task task, boolean finished) {
        if (task == null)
            return;
        Task taskFromDb = this.taskRepository.findOne(task.getId());
        taskFromDb.setFinished(finished);
    }

}
