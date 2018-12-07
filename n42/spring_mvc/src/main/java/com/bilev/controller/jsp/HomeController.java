package com.bilev.controller.jsp;

import com.bilev.messaging.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    MessageSender messageSender;

    @GetMapping(value = "/")
    public String home() {
        messageSender.sendMessage();
        return "home";
    }

    @GetMapping(value = "/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

}
