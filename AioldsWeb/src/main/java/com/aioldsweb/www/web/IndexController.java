package com.aioldsweb.www.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    public IndexController() {
    }

    @RequestMapping("/home")
    public String showIndexPage() {
        return "home";
    }
}
