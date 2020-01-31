package ru.alibaev.chat.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alibaev.chat.entity.User;
import ru.alibaev.chat.service.UserService;


@Controller
@RequestMapping("registration")
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registration (Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String register (Model model, User user) {
        userService.createUser(user);
        return "redirect:/login";
    }


}
