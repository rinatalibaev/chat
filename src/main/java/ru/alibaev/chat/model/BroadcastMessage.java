package ru.alibaev.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BroadcastMessage {

    private Long id;
    private String fromUser;
    private String toUser;
    private String content;
    private LocalDateTime created;
}
