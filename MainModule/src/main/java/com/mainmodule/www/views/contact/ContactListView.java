package com.mainmodule.www.views.contact;

import com.aioldsweb.www.model.entity.ContactEntity;
import com.aioldsweb.www.view.Impl.ContactViewImpl;
import com.mainmodule.www.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PageTitle(ContactEntity.TITLE)
@Route(value = ContactEntity.VIEW_ROUTE, layout = MainLayout.class)
@PermitAll
public class ContactListView extends VerticalLayout {

    private final ContactViewImpl contactView;

    public ContactListView(ContactViewImpl contactView) {
        this.contactView = contactView;

        add(contactView.initUI());
    }
}
