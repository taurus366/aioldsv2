package com.aioldsweb.www.web;

import com.aioldsweb.www.model.enums.SubjectEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    public IndexController() {
    }

    @GetMapping("home")
    public String showIndexPage(Model model) {

        model
                .addAttribute("subjects", SubjectEnum.values());

        return "home";
    }

    @PostMapping("/contact")
    public void contactForm() {

    }
}
