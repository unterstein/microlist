package de.micromata.labs.todo.frontend.login;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.authentication.IAuthenticationStrategy;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import de.micromata.labs.todo.frontend.feedback.TodoFeedbackPanel;

/**
 * 
 * @author <a href="mailto:j.unterstein@micromata.de">Johannes Unterstein</a>
 * 
 */
public class LoginPanel extends Panel {
    private String email;
    private String password;
    private boolean rememberMe = true;
    private Form<LoginPanel> loginForm;

    public LoginPanel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
        this.loginForm = new Form<LoginPanel>("form", new CompoundPropertyModel<LoginPanel>(this));
        add(this.loginForm);
        this.loginForm.add(new TodoFeedbackPanel("feedback") {
            @Override
            public boolean isVisible() {
                return LoginPanel.this.loginForm.hasError();
            }
        });
        // the email field
        TextField<String> email = new TextField<String>("email");
        email.add(EmailAddressValidator.getInstance());
        email.add(new AttributeModifier("placeholder", new ResourceModel("email")));
        email.setRequired(true);
        this.loginForm.add(email);
        // the password field
        PasswordTextField password = new PasswordTextField("password");
        password.add(new AttributeModifier("placeholder", new ResourceModel("password")));
        password.setRequired(true);
        this.loginForm.add(password);
        this.loginForm.add(new AjaxSubmitLink("submit", this.loginForm) {

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(LoginPanel.this);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                IAuthenticationStrategy strategy = getApplication().getSecuritySettings().getAuthenticationStrategy();
                if (signIn(getEmail(), getPassword())) {
                    if (isRememberMe()) {
                        strategy.save(getEmail(), getPassword());
                    } else {
                        strategy.remove();
                    }
                    onSignInSucceeded(target);
                } else {
                    onSignInFailed(target);
                    strategy.remove();
                }
            }
        });
        this.loginForm.add(new CheckBox("rememberMe", Model.of(true)));
    }

    /**
     * Called when sign in failed
     */
    protected void onSignInFailed(AjaxRequestTarget target) {
        // Try the component based localizer first. If not found try the
        // application localizer. Else use the default
        this.loginForm.error(getString("error.signInFailed"));
        target.add(this);
    }

    /**
     * Called when sign in was successful
     */
    protected void onSignInSucceeded(AjaxRequestTarget target) {
        // If login has been called because the user was not yet logged in, than
        // continue to the
        // original destination, otherwise to the Home page
        if (!continueToOriginalDestination()) {
            setResponsePage(getApplication().getHomePage());
        }
    }

    /**
     * @see org.apache.wicket.Component#onBeforeRender()
     */
    @Override
    protected void onBeforeRender() {
        // logged in already?
        if (isSignedIn() == false) {
            IAuthenticationStrategy authenticationStrategy = getApplication().getSecuritySettings().getAuthenticationStrategy();
            // get username and password from persistence store
            String[] data = authenticationStrategy.load();

            if ((data != null) && (data.length > 1)) {
                // try to sign in the user
                if (signIn(data[0], data[1])) {
                    this.email = data[0];
                    this.password = data[1];

                    checkRestartResponseAndContinue();
                } else {
                    // the loaded credentials are wrong. erase them.
                    authenticationStrategy.remove();
                }
            }
        }

        // don't forget
        super.onBeforeRender();
    }

    private void checkRestartResponseAndContinue() {
        // logon successful. Continue to the original destination
        if (!continueToOriginalDestination()) {
            // Ups, no original destination. Go to the home page
            throw new RestartResponseException(getSession().getPageFactory().newPage(getApplication().getHomePage()));
        }
    }

    protected boolean signIn(String userName, String password) {
        return AuthenticatedWebSession.get().signIn(userName, password);
    }

    protected boolean isSignedIn() {
        return AuthenticatedWebSession.get().isSignedIn();
    }

    protected String getPassword() {
        return this.password;
    }

    protected String getEmail() {
        return this.email;
    }

    protected boolean isRememberMe() {
        return this.rememberMe;
    }

}
