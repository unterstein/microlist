package de.micromata.labs.todo.frontend;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.micromata.labs.todo.exceptions.UserAlreadyExistsException;
import de.micromata.labs.todo.model.User;
import de.micromata.labs.todo.services.ITodoService;

/**
 * 
 * @author <a href="mailto:j.unterstein@micromata.de">Johannes Unterstein</a>
 * 
 */
public class TodoSession extends AuthenticatedWebSession {

    @SpringBean
    private ITodoService service;

    private User user;
    private static final Roles BASIC_ROLES = new Roles(ITodoService.ROLE_LOGGEDIN);

    public TodoSession(Request request) {
        super(request);
        Injector.get().inject(this);
    }

    @Override
    public Roles getRoles() {
        return this.user != null ? BASIC_ROLES : null;
    }

    @Override
    public boolean authenticate(String username, String password) {
        this.user = this.service.signIn(username, password);
        return this.user != null;
    }

    public static TodoSession get() {
        return (TodoSession) Session.get();
    }

    public void register(User user) throws UserAlreadyExistsException {
        this.user = this.service.register(user);
    }
}
