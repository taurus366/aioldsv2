package com.mainmodule.www.views.group;

import com.mainmodule.www.views.MainLayout;
import com.profilemodule.www.model.entity.GroupEntity;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle(GroupEntity.TITLE)
@Route(value = GroupEntity.VIEW_ROUTE, layout = MainLayout.class)
@RolesAllowed({GroupEntity.VIEW_ROLE})
public class GroupListView extends VerticalLayout {
}
