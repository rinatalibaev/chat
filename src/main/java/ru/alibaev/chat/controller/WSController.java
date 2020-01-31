package ru.alibaev.chat.controller;

import org.modelmapper.ModelMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import ru.alibaev.chat.dto.MessageDTO;
import ru.alibaev.chat.model.BroadcastMessage;

import java.time.LocalDateTime;

@Controller
public class WSController {

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public BroadcastMessage getUser(MessageDTO messageDTO) {
        ModelMapper modelMapper = new ModelMapper();
        BroadcastMessage broadcastMessage = modelMapper.map(messageDTO, BroadcastMessage.class);
        broadcastMessage.setCreated(LocalDateTime.now());
        return broadcastMessage;
    }
}
