package com.mainmodule.www.views.contractor.sideNav;

import com.customermodule.www.model.entity.CustomerEntity;
import com.mainmodule.www.views.contractor.CustomerView;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNavItem;
import org.springframework.stereotype.Component;

@Component
public class ContactorSideNav {

    private final static String TITLE = "Contractor";

    public static SideNavItem initNav() {
        SideNavItem navItem = new SideNavItem(TITLE);
        navItem.setPrefixComponent(VaadinIcon.PENCIL.create());

        SideNavItem item = new SideNavItem(CustomerEntity.TITLE, CustomerView.class, CustomerEntity.icon.create());
        navItem.addItem(item);



        return navItem;
    }
}
