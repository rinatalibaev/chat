package ru.alibaev.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alibaev.chat.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername (String username);

    boolean existsByUsername(String username);
}
