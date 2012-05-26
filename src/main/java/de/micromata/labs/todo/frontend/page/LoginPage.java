package de.micromata.labs.todo.frontend.page;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.micromata.labs.todo.frontend.login.LoginPanel;
import de.micromata.labs.todo.frontend.login.RegisterPanel;

/**
 * 
 * @author <a href="mailto:j.unterstein@micromata.de">Johannes Unterstein</a>
 * 
 */
public class LoginPage extends AbstractTodoPage {
    private boolean registerVisible;

    public LoginPage(PageParameters parameters) {
        super(parameters);
        final WebMarkupContainer container = new WebMarkupContainer("container");
        container.setOutputMarkupId(true);
        add(container);

        this.registerVisible = true;
        container.add(new LoginPanel("siginPanel"));
        container.add(new AjaxLink<Void>("registerLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                LoginPage.this.registerVisible = !LoginPage.this.registerVisible;
                target.add(container);
            }
        });
        container.add(new RegisterPanel("register") {
            @Override
            public boolean isVisible() {
                return !LoginPage.this.registerVisible;
            }
        });
    }
}
