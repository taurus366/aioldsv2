package com.aioldsweb.www.model.bind;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class ContactBindModel {

    private String email;
    private String names;
    private int subject;
    private String message;


}
