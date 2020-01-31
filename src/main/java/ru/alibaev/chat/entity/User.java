package ru.alibaev.chat.entity;

import lombok.Data;
import ru.alibaev.chat.dto.UserDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String password;

    @ManyToMany(cascade={CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name="user_role",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles;

//    @Transient
//    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
//    private List<Message> myMessages;

//    @Transient
//    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
//    private List<Message> forMeMessages;

    private boolean enabled;

    public UserDTO toDTO () {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(getId());
        userDTO.setUsername(getUsername());
        userDTO.setPassword("Скрыто");
        userDTO.setEnabled(isEnabled());
        List<String> roleNames = new ArrayList<>();
        for(Role role : getRoles()) {
            roleNames.add(role.getName());
        }
        userDTO.setRoles(roleNames);
        return userDTO;
    }

}
