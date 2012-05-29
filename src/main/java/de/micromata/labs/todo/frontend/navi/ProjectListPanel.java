package de.micromata.labs.todo.frontend.navi;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.PropertyModel;
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
        add(new AjaxLink<Void>("addLink") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                ProjectListPanel.this.service.addProject(TodoSession.get().getUser(), new Project(getString("project.defaultName")));
                target.add(ProjectListPanel.this);
            }
        });
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        this.projectRepeater.removeAll();
        for (final Project project : this.service.getProjects(TodoSession.get().getUser())) {
            WebMarkupContainer repeatingComponent = new WebMarkupContainer(this.projectRepeater.newChildId());
            this.projectRepeater.add(repeatingComponent);
            final AjaxEditableLabel<String> editableLabel = new AjaxEditableLabel<String>("label", new PropertyModel<String>(project,
                    "name")) {
                @Override
                public void onSubmit(AjaxRequestTarget target) {
                    ProjectListPanel.this.service.editProject(project);
                    target.add(ProjectListPanel.this);
                }

                @Override
                public void onEdit(AjaxRequestTarget target) {
                    super.onEdit(target);
                }
            };
            editableLabel.setOutputMarkupId(true);
            repeatingComponent.add(editableLabel);
            repeatingComponent.add(new AjaxEventBehavior("onClick") {
                @Override
                protected void onEvent(AjaxRequestTarget target) {

                }
            });
        }
    }
}
