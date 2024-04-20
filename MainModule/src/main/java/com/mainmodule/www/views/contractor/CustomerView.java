package com.mainmodule.www.views.contractor;

import com.customermodule.www.model.entity.CustomerEntity;
import com.customermodule.www.view.Impl.customer.CustomerListViewImpl;
import com.mainmodule.www.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PageTitle(CustomerEntity.TITLE)
@Route(value = CustomerEntity.VIEW_ROUTE, layout = MainLayout.class)
@PermitAll
public class CustomerView extends VerticalLayout {

    private final CustomerListViewImpl customerListViewImpl;

    public CustomerView(CustomerListViewImpl customerListViewImpl) {
        this.customerListViewImpl = customerListViewImpl;


        add(customerListViewImpl.initUI());
    }
}
