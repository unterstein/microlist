package de.micromata.labs.todo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.joda.time.DateTime;

/**
 * 
 * @author <a href="mailto:j.unterstein@micromata.de">Johannes Unterstein</a>
 * 
 */
@Entity(name = "todo_task")
@XmlTransient
public class Task extends AbstractTodoBean {
    private User user;
    private String title;
    private String description;
    private boolean finished;
    private Long dueDateLong;
    private DateTime dueDate;
    private Long id;
    private Project project;

    public Task() {

    }

    @OneToOne
    public User getUser() {
        return this.user;
    }

    @ManyToOne
    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Transient
    @XmlTransient
    public DateTime getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(DateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Long getDueDateLong() {
        return this.dueDateLong;
    }

    public void setDueDateLong(Long dueDateLong) {
        this.dueDateLong = dueDateLong;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return this.id;
    }
}
