package com.mainmodule.www.views.city;

import com.mainmodule.www.views.MainLayout;
import com.profilemodule.www.model.entity.CityEntity;
import com.profilemodule.www.view.Impl.city.CityListViewImpl;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PageTitle(CityEntity.TITLE)
@Route(value = CityEntity.VIEW_ROUTE, layout = MainLayout.class)
@PermitAll
public class CityView extends VerticalLayout {

    private final CityListViewImpl cityListView;

    public CityView(CityListViewImpl cityListView) {
        this.cityListView = cityListView;

        add(cityListView.initUI());
    }
}
