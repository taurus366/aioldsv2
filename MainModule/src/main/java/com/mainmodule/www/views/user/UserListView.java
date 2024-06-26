package com.mainmodule.www.views.user;

import com.mainmodule.www.views.MainLayout;
import com.profilemodule.www.model.entity.UserEntity;
import com.profilemodule.www.view.Impl.user.UserListViewImpl;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

//@PageTitle(UserEntity.TITLE)
@Route(value = UserEntity.VIEW_ROUTE, layout = MainLayout.class)
@RolesAllowed({UserEntity.VIEW_ROLE})
public class UserListView extends VerticalLayout {

        private final UserListViewImpl userList;


    @Override
    protected void onAttach(AttachEvent attachEvent) {
       userList.setAttach(attachEvent);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        userList.setDettach(detachEvent);
    }

    public UserListView(UserListViewImpl userList) {
        this.userList = userList;


        add(userList.initUI());


    }
}
