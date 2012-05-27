package de.micromata.labs.todo.frontend.todolist;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.micromata.labs.todo.frontend.AbstractTodoPage;
import de.micromata.labs.todo.frontend.TodoApplication;
import de.micromata.labs.todo.frontend.TodoSession;
import de.micromata.labs.todo.services.ITodoService;

/**
 * 
 * @author <a href="mailto:j.unterstein@micromata.de">Johannes Unterstein</a>
 * 
 */
@AuthorizeInstantiation(ITodoService.ROLE_LOGGEDIN)
public class HomePage extends AbstractTodoPage {

    public HomePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void buildNavigation(RepeatingView navigation, String linkWicketId) {
        WebMarkupContainer container = new WebMarkupContainer(navigation.newChildId());
        navigation.add(container);
        // container.add(new BookmarkablePageLink<T>(abstractLinkWicketId, pageClass)
        container.add(new Link<Void>(linkWicketId) {
            public void onClick() {
                TodoSession.get().invalidate();
                setResponsePage(TodoApplication.get().getHomePage());
            }
        }.setBody(new ResourceModel("logout")));
    }
}
