package ru.alibaev.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alibaev.chat.entity.Message;

import javax.persistence.NamedNativeQuery;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    void deleteMessagesByFromUser_Id(Long userId);

}
