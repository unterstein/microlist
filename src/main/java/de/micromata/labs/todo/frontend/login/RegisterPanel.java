package de.micromata.labs.todo.frontend.login;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import de.micromata.labs.todo.exceptions.UserAlreadyExistsException;
import de.micromata.labs.todo.frontend.TodoSession;
import de.micromata.labs.todo.frontend.feedback.TodoFeedbackPanel;
import de.micromata.labs.todo.model.User;

/**
 * 
 * @author <a href="mailto:j.unterstein@micromata.de">Johannes Unterstein</a>
 * 
 */
public class RegisterPanel extends Panel {

    public RegisterPanel(String id) {
        super(id);
        setOutputMarkupId(true);
        final Form<User> registerForm = new Form<User>("form", new CompoundPropertyModel<User>(new User()));
        add(registerForm);
        registerForm.add(new TodoFeedbackPanel("feedback") {
            @Override
            public boolean isVisible() {
                return registerForm.hasError();
            }
        });
        // the email field
        TextField<String> email = new TextField<String>("email");
        email.add(EmailAddressValidator.getInstance());
        email.add(new AttributeModifier("placeholder", new ResourceModel("email")));
        registerForm.add(email);
        // the password field
        PasswordTextField password = new PasswordTextField("password");
        password.add(new AttributeModifier("placeholder", new ResourceModel("password")));
        registerForm.add(password);
        registerForm.add(new AjaxSubmitLink("submit", registerForm) {

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(RegisterPanel.this);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    TodoSession.get().register(registerForm.getModelObject());
                    checkRestartResponseAndContinue();
                } catch (UserAlreadyExistsException e) {
                    registerForm.error(getString("error.duplicateEmail"));
                    target.add(RegisterPanel.this);
                }
            }
        });
    }

    private void checkRestartResponseAndContinue() {
        // logon successful. Continue to the original destination
        if (!continueToOriginalDestination()) {
            // Ups, no original destination. Go to the home page
            throw new RestartResponseException(getSession().getPageFactory().newPage(getApplication().getHomePage()));
        }
    }
}
