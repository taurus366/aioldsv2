package com.mainmodule.www.views.user;

import com.mainmodule.www.views.MainLayout;
import com.profilemodule.www.model.entity.UserEntity;
import com.profilemodule.www.view.user.UserListImpl;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@PageTitle("User list")
@Route(value = UserListImpl.VIEW, layout = MainLayout.class)
@RolesAllowed({UserEntity.VIEW})
public class UserListView extends VerticalLayout {

        private final UserListImpl userList;


    public UserListView(UserListImpl userList) {
        this.userList = userList;


        add(userList.initUI());


    }
}
