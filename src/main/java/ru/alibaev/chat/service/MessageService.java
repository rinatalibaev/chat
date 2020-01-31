package ru.alibaev.chat.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alibaev.chat.dto.MessageDTO;
import ru.alibaev.chat.entity.Message;
import ru.alibaev.chat.entity.User;
import ru.alibaev.chat.repository.MessageRepository;
import ru.alibaev.chat.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Message saveMessage(MessageDTO messageDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Message message = modelMapper.map(messageDTO, Message.class);
        User fromUser = userRepository.findByUsername(messageDTO.getFromUser());
        User toUser = userRepository.findByUsername(messageDTO.getToUser());
        message.setFromUser(fromUser);
        message.setToUser(toUser);
        message.setCreated(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<MessageDTO> getAll() {
        return messageRepository.findAll().stream().map(Message::toDTO).collect(Collectors.toList());
    }

    public Message getById(Long id) {
        return messageRepository.getOne(id);
    }

    public void patchById(Long id, String newContent) {
        Message message = messageRepository.getOne(id);
        message.setContent(newContent);
        messageRepository.save(message);
    }

    public void delete(Long id) {
        messageRepository.deleteById(id);
    }
}
