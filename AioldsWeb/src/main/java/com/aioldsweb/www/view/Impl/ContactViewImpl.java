package com.aioldsweb.www.view.Impl;

import com.aioldsweb.www.model.entity.ContactEntity;
import com.aioldsweb.www.model.service.ContactService;
import com.profilemodule.www.shared.grids.GridList;
import com.profilemodule.www.shared.model.dto.GridListDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ContactViewImpl extends VerticalLayout {

    public final int NOTIFY_DURATION = 5000;
    public final Notification.Position NOTIFY_POSITION = Notification.Position.BOTTOM_STRETCH;
    public final String PATTERN_FORMAT = "dd/MM/yyyy HH:mm";

    private final ContactService contactService;

    public ContactViewImpl(ContactService contactService) {
        this.contactService = contactService;
    }

    public VerticalLayout initUI() {
        VerticalLayout verticalLayout = initGrid();

        return verticalLayout;
    }

    GridListDto gridListDto;
    private VerticalLayout initGrid() {
        gridListDto = GridList.initGrid(ContactEntity.class);
        VerticalLayout verticalLayout = gridListDto.getVerticalLayout();
        initBtns(gridListDto.getReload(), gridListDto.getCreate());

        reloadGrid();
        return verticalLayout;
    }

    private void initBtns(Button reload, Button create) {

        reload.addClickListener(event -> reloadGrid());
    }

    private void reloadGrid() {
        final List<ContactEntity> all = contactService.getAll();
        @SuppressWarnings("unchecked")
        Grid<ContactEntity> grid = (Grid<ContactEntity>) gridListDto.getGrid();
            grid.setItems(all);
    }
}
