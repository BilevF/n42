package com.bilev.controller.jsp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping(value = "/")
    public String home() {
        return "home";
    }

    @GetMapping(value = "/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

}
