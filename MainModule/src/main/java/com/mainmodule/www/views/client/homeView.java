package com.mainmodule.www.views.client;

import com.profilemodule.www.config.security.AuthenticatedUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route(value = "")
@RouteAlias(value = "")
@PageTitle("Home")
public class homeView extends FlexLayout implements BeforeEnterObserver {

    private final AuthenticatedUser user;
    public homeView(AuthenticatedUser user) {

        this.user = user;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        if(user.get().isPresent()) {
            event.forwardTo("user");
        } else {
            UI.getCurrent().getPage().setLocation("/home");
        }
    }
}
