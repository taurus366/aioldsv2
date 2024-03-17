package com.mainmodule.www.views.language;

import com.mainmodule.www.views.MainLayout;
import com.profilemodule.www.model.entity.LanguageEntity;
import com.profilemodule.www.view.Impl.language.LanguageListViewImpl;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle(LanguageEntity.TITLE)
@Route(value = LanguageEntity.VIEW_ROUTE, layout = MainLayout.class)
@RolesAllowed({LanguageEntity.VIEW_ROLE})
public class LanguageListView extends VerticalLayout {

    private final LanguageListViewImpl languageListView;

    public LanguageListView(LanguageListViewImpl languageListView) {
        this.languageListView = languageListView;

        add(languageListView.initUI());
    }
}
