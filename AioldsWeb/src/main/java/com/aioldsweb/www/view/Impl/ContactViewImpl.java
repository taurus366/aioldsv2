package com.aioldsweb.www.view.Impl;

import com.aioldsweb.www.model.entity.ContactEntity;
import com.aioldsweb.www.model.enums.SubjectEnum;
import com.aioldsweb.www.model.service.ContactService;
import com.profilemodule.www.shared.grids.GridList;
import com.profilemodule.www.shared.model.dto.GridListDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ContactViewImpl extends VerticalLayout {

    public final int NOTIFY_DURATION = 5000;
    public final String UPDATED_CONTACT_MESSAGE = "Successfully updated Contact";
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
        gridListDto.getGrid().addItemDoubleClickListener(event -> createUpdateItemDialog((ItemDoubleClickEvent<ContactEntity>) event).open());


        reloadGrid();
        return verticalLayout;
    }

    private void initBtns(Button reload, Button create) {

        create.setVisible(false);
        reload.addClickListener(event -> reloadGrid());
    }

    private Dialog createUpdateItemDialog(ItemDoubleClickEvent<ContactEntity> event) {
        Dialog updateItemDialog = initUpdateItemDialog(event.getItem());

        Button cancelBtn = new Button(VaadinIcon.CLOSE.create(), ev -> updateItemDialog.close());
        cancelBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button saveBtn = new Button(VaadinIcon.CHECK.create(), ev -> {
            final ContactEntity item = event.getItem();
            item.setRead(isRead.getValue());
            contactService.save(event.getItem());
            cancelBtn.click();
            Notification.show(UPDATED_CONTACT_MESSAGE, NOTIFY_DURATION, NOTIFY_POSITION)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            reloadGrid();
        });



        updateItemDialog.getFooter().add(saveBtn, cancelBtn);
        return updateItemDialog;
    }

    private Select<Boolean> isRead = new Select<>();
    private Dialog initUpdateItemDialog(ContactEntity entity) {
        final FlexLayout flexLayout = new FlexLayout(new H2("Contact #" + entity.getId()));
        flexLayout.setSizeFull();
        flexLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        Dialog dialog = new Dialog();
        dialog.getHeader().add(flexLayout);
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(false);
        dialog.setDraggable(true);
        dialog.setResizable(true);


        VerticalLayout verticalLayout = new VerticalLayout();

        isRead = new Select<>();
        isRead.setLabel("Status");
        isRead.setItems(true, false);
        isRead.setValue(entity.isRead());
        isRead.setSizeFull();

        TextField field = new TextField();
        field.setSizeFull();
        field.setLabel("Names");
        field.setValue(entity.getNames() != null ? entity.getNames() : "");
        field.setEnabled(false);

        EmailField emailField = new EmailField();
        emailField.setSizeFull();
        emailField.setLabel("Email");
        emailField.setValue(entity.getEmail() != null ? entity.getEmail() : "");
        emailField.setEnabled(false);


        Select<SubjectEnum> selectEnum = new Select<>();
        selectEnum.setSizeFull();
        selectEnum.setItems(SubjectEnum.values());
        selectEnum.setValue(entity.getSubject());
        selectEnum.setEnabled(false);
        selectEnum.setLabel("Type");

        TextArea textArea = new TextArea();
        textArea.setMinHeight("100px");
        textArea.setMaxHeight("150px");
        textArea.getStyle().set("overflow", "auto");
        textArea.scrollIntoView();
        textArea.setReadOnly(true);
        textArea.setSizeFull();
        textArea.setLabel("Message");
        textArea.setValue(entity.getMessage() != null ? entity.getMessage() : "");

        verticalLayout.add(field, emailField, selectEnum, textArea, isRead);
        dialog.add(verticalLayout);
        return dialog;
    }

    private void reloadGrid() {
        final List<ContactEntity> all = contactService.getAll();
        @SuppressWarnings("unchecked")
        Grid<ContactEntity> grid = (Grid<ContactEntity>) gridListDto.getGrid();
            grid.setItems(all);
    }
}
