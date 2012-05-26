package de.micromata.labs.todo.frontend.feedback;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class TodoFeedbackPanel extends FeedbackPanel {

    public TodoFeedbackPanel(String id, IFeedbackMessageFilter filter) {
        super(id, filter);
    }

    public TodoFeedbackPanel(String id) {
        super(id);
    }

    @Override
    protected String getCSSClass(FeedbackMessage message) {
        return "";
    }
}
