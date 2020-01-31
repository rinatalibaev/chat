package ru.alibaev.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.alibaev.chat.dto.MessageDTO;
import ru.alibaev.chat.entity.Message;
import ru.alibaev.chat.entity.Role;
import ru.alibaev.chat.entity.User;
import ru.alibaev.chat.security.CustomUserDetailsService;
import ru.alibaev.chat.service.MessageService;
import ru.alibaev.chat.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Controller
@RequestMapping("chat")
public class MessageController {

    private MessageService messageService;
    private UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping
    public String getMessages (Model model) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        List<String> userNameList = userService.getAllUsers().stream().map(User::getUsername).collect(Collectors.toList());
        boolean userIsAdmin = false;
        if (request != null) {
            String userName = request.getRemoteUser();
            userNameList.remove(userName);
            List<Role> userRoles = userService.getUserByName(userName).getRoles();
            for (Role role : userRoles) {
                if (role.getName().equals("ROLE_ADMIN")) {
                    userIsAdmin = true;
                    break;
                }
            }
        }
        model.addAttribute("userIsAdmin", userIsAdmin);
        model.addAttribute("usersList", userNameList);
        return "chat";
    }

    @GetMapping("/messages")
    public @ResponseBody List<MessageDTO> getMessages () {
        return messageService.getAll();
    }

    @PostMapping
    public @ResponseBody MessageDTO postMessage (@RequestBody MessageDTO messageDTO) {
        return messageService.saveMessage(messageDTO).toDTO();
    }

    @GetMapping("/{id}")
    public @ResponseBody Message getMessage (@PathVariable("id") Long id) {
        return messageService.getById(id);
    }

    @PatchMapping("/{id}")
    public @ResponseBody void patchMessage (@PathVariable("id") Long id, @RequestParam String newContent) {
        messageService.patchById(id, newContent);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody void delete (@PathVariable("id") Long id) {
        messageService.delete(id);
    }

}
