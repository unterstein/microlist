package de.micromata.labs.todo.frontend.navi;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.micromata.labs.todo.frontend.TodoSession;
import de.micromata.labs.todo.model.Project;
import de.micromata.labs.todo.services.ITodoService;

/**
 * 
 * @author <a href="mailto:j.unterstein@micromata.de">Johannes Unterstein</a>
 * 
 */
public class ProjectListPanel extends Panel {

    @SpringBean
    private ITodoService service;
    private RepeatingView projectRepeater;

    public ProjectListPanel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);
        this.projectRepeater = new RepeatingView("projects");
        add(this.projectRepeater);
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        this.projectRepeater.removeAll();
        for (Project project : this.service.getProjects(TodoSession.get().getUser())) {
            WebMarkupContainer repeatingComponent = new WebMarkupContainer(this.projectRepeater.newChildId());
            this.projectRepeater.add(repeatingComponent);
            Link<Void> projectLink = new Link<Void>("projectLink") {
                @Override
                public void onClick() {

                }
            };
            projectLink.setBody(Model.of(project.getName()));
            repeatingComponent.add(projectLink);
        }
    }
}
