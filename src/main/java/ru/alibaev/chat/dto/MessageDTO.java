package ru.alibaev.chat.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private Long id;
    private String fromUser;
    private String toUser;
    private String content;
    private LocalDateTime created;
}
