package de.micromata.labs.todo.frontend;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import de.micromata.labs.todo.frontend.login.LoginPage;
import de.micromata.labs.todo.frontend.todolist.HomePage;

/**
 * 
 * @author <a href="mailto:j.unterstein@micromata.de">Johannes Unterstein</a>
 * 
 */
public class TodoApplication extends AuthenticatedWebApplication implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public Class<? extends AbstractTodoPage> getHomePage() {
        return HomePage.class;
    }

    @Override
    public void init() {
        super.init();

        // init instantiation listener
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, this.applicationContext, true));
        Injector.get().inject(this);

        // mount pages
        mountPage("login", LoginPage.class);
        mountPage("home", HomePage.class);

        // strip <wicket:tags />
        getMarkupSettings().setStripWicketTags(true);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return LoginPage.class;
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return TodoSession.class;
    }

    public static TodoApplication get() {
        return (TodoApplication) AuthenticatedWebApplication.get();
    }
}
