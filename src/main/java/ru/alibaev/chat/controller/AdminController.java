package ru.alibaev.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping
    public String adminPage (Model model) {
        return "admin";
    }

    @GetMapping("messages")
    public String adminMessagesPage (Model model) {
        return "admin_messages";
    }

}
