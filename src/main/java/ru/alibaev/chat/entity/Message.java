package ru.alibaev.chat.entity;

import lombok.Data;
import org.springframework.lang.Nullable;
import ru.alibaev.chat.dto.MessageDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import org.hibernate.annotations.NamedQuery;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FROM_USER")
    @NotNull
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "TO_USER")
    @Nullable
    private User toUser;

    @Size(min = 1)
    private String content;

    private LocalDateTime created;

    public MessageDTO toDTO () {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(getId());
        messageDTO.setContent(getContent());
        messageDTO.setCreated(getCreated());
        messageDTO.setFromUser(getFromUser().getUsername());
        if (getToUser() != null) {
            messageDTO.setToUser(getToUser().getUsername());
        }
        return messageDTO;
    }

}
