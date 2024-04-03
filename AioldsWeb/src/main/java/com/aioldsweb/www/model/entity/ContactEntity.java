package com.aioldsweb.www.model.entity;

import com.aioldsweb.www.model.enums.SubjectEnum;
import com.profilemodule.www.model.entity.BaseEntity;
import com.vaadin.flow.component.icon.VaadinIcon;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Entity
@Table(name = "aiolds_contact")
public class ContactEntity extends BaseEntity {
    public static final String SCOPE = "CONTACT";
    public static final String VIEW_ROLE = SCOPE + "_VIEW";
    public static final String READ_ROLE = SCOPE + "_READ";
    public static final String UPDATE_ROLE = SCOPE + "_UPDATE";
    public static final String DELETE_ROLE = SCOPE + "_DELETE";
    public static final String ADD_ROLE = SCOPE + "_ADD";
    public static final String TITLE = "Contact list";
    public static final String VIEW_ROUTE = "contact_list";
    public static final VaadinIcon icon = VaadinIcon.QUESTION_CIRCLE;

    @Column
    private String names;

    @Column
    private String email;

    @Column
    private SubjectEnum subject;

    @Column
    private String message;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isRead = false;


}
