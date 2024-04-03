package com.aioldsweb.www.web;

import com.aioldsweb.www.model.bind.ContactBindModel;
import com.aioldsweb.www.model.entity.ContactEntity;
import com.aioldsweb.www.model.enums.SubjectEnum;
import com.aioldsweb.www.model.service.ContactService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/")
@AnonymousAllowed
public class IndexController {

    private final ContactService contactService;
    public IndexController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("home")
    public String showIndexPage(Model model) {

        model
                .addAttribute("subjects", SubjectEnum.values());

        return "home";
    }

    @PostMapping("api/contact")
    public Object contactForm(ContactBindModel contactBindModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("status", "error"));
        }

       final ContactEntity newContact = ContactEntity.builder()
                .names(contactBindModel.getNames())
                .email(contactBindModel.getEmail())
                .message(contactBindModel.getMessage())
                .subject(SubjectEnum.values()[contactBindModel.getSubject()])
                .build();

        contactService.save(newContact);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "ok"));
    }
}
