package ru.alibaev.chat.dto;

import lombok.Data;
import ru.alibaev.chat.entity.Role;

import javax.persistence.*;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private List<String> roles;
    private boolean enabled;
}
