package com.mainmodule.www.views.client;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route(value = "")
@RouteAlias(value = "")
@PageTitle("Home")
public class homeView extends FlexLayout implements BeforeEnterObserver {

    public homeView() {

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
                UI.getCurrent().getPage().setLocation("/home");
    }
}
