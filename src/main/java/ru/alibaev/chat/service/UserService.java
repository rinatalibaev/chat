package ru.alibaev.chat.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alibaev.chat.dto.UserDTO;
import ru.alibaev.chat.entity.User;
import ru.alibaev.chat.repository.MessageRepository;
import ru.alibaev.chat.repository.RoleRepository;
import ru.alibaev.chat.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private MessageRepository messageRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.messageRepository = messageRepository;
    }

    public String createUser(User user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setEnabled(true);
            userRepository.save(user);
            return "redirect:/";
        } else {
            return "redirect:/registration?exist";
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser (UserDTO userDTO) {
        User user = userRepository.getOne(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEnabled(userDTO.isEnabled());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        messageRepository.deleteMessagesByFromUser_Id(userId);
        userRepository.deleteById(userId);
    }

    public User getUserByName (String userName) {
        return userRepository.findByUsername(userName);
    }
}
