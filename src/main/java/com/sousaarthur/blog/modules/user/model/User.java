package com.sousaarthur.blog.modules.user.model;

import com.sousaarthur.blog.modules.auth.model.Login;
import com.sousaarthur.blog.modules.user.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String bio;
    @Column(name = "avatar_url")
    private String avatar;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    private String linkedin;
    private String github;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "login_id")
    private Login login;

    public User(String name, Login login){
        this.name = name;
        this.login = login;
        this.createdAt = LocalDateTime.now();
    }

    public User(UserDTO dto, Login login){
        this.id = dto.id();
        this.name = dto.name();
        this.bio = dto.bio();
        this.avatar = dto.avatar();
        this.linkedin = dto.linkedin();
        this.github = dto.github();
        this.login = login;
    }

    public User toModel(UserDTO dto){
        return User.builder()
                .name(dto.name())
                .bio(dto.bio())
                .avatar(dto.avatar())
                .linkedin(dto.linkedin())
                .github(dto.github())
                .build();
    }
}
