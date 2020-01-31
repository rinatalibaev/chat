package ru.alibaev.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.alibaev.chat.dto.MessageDTO;
import ru.alibaev.chat.dto.UserDTO;
import ru.alibaev.chat.entity.Message;
import ru.alibaev.chat.entity.User;
import ru.alibaev.chat.service.MessageService;
import ru.alibaev.chat.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Controller
@RequestMapping("users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public @ResponseBody List<UserDTO> getMessages () {
        return userService.getAllUsers().stream().map(User::toDTO).collect(Collectors.toList());
    }

    @PutMapping
    public @ResponseBody void updateUser (@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
    }

    @DeleteMapping
    public @ResponseBody void deleteUser (@RequestParam Long userId) {
        userService.deleteUser(userId);
    }

}
