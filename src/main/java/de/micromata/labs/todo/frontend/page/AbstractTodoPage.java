package de.micromata.labs.todo.frontend.page;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * 
 * @author <a href="mailto:j.unterstein@micromata.de">Johannes Unterstein</a>
 * 
 */
public abstract class AbstractTodoPage extends WebPage {

    public AbstractTodoPage(final PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Label("title", new ResourceModel("html.title")));
        RepeatingView navigation = new RepeatingView("navigation");
        add(navigation);
        buildInteralNavigation(navigation, "navigationLink");
    }

    private void buildInteralNavigation(RepeatingView navigation, String linkWicketId) {
        navigation.add(createUrlLinkNavigationElement(navigation.newChildId(), linkWicketId, new ResourceModel("github.link"),
                new ResourceModel("github.url")));
        buildNavigation(navigation, linkWicketId);
    }

    /**
     * Hook method where specializing pages can register their navigation link elements
     * 
     * @param navigation
     * @param linkWicketId
     */
    protected void buildNavigation(RepeatingView navigation, String linkWicketId) {

    }

    protected final WebMarkupContainer createPageLinkNavigationElement(String containerWicketId, String linkWicketId, IModel<String> label,
            Class<? extends WebPage> page, PageParameters parameters) {
        WebMarkupContainer container = new WebMarkupContainer(containerWicketId);
        container.add(new BookmarkablePageLink<Void>(linkWicketId, page, parameters).setBody(label));
        return container;
    }

    protected final WebMarkupContainer createUrlLinkNavigationElement(String containerWicketId, String linkWicketId, IModel<String> label,
            IModel<String> url) {
        WebMarkupContainer container = new WebMarkupContainer(containerWicketId);
        ExternalLink link = new ExternalLink(linkWicketId, url, label);
        link.add(new AttributeModifier("target", Model.of("_blank")));
        container.add(link);
        return container;
    }
}
