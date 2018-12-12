package com.bilev.controller.jsp;

import com.bilev.messaging.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
