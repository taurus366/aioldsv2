package com.mainmodule.www.views.profile;

import com.mainmodule.www.views.MainLayout;
import com.profilemodule.www.view.Impl.profile.ProfileViewImpl;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PageTitle(ProfileViewImpl.TITLE)
@Route(value = ProfileViewImpl.VIEW_ROUTE, layout = MainLayout.class)
@PermitAll
public class ProfilView extends VerticalLayout {

    private final ProfileViewImpl profileView;

    public ProfilView(ProfileViewImpl profileView) {
        this.profileView = profileView;

        add(profileView.initUI());
    }
}
