package com.sousaarthur.blog.modules.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "login")
@Table(name = "login")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Login implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    private String password;
    private UserRole role;
    private boolean active;

    public Login(String login, String password, UserRole role){
        this.login = login;
        this.password = password;
        this.role = role;
        this.active = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.OWNER){
            return List.of(new SimpleGrantedAuthority("ROLE_OWNER"), new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_WRITER"), new SimpleGrantedAuthority("ROLE_READER"));
        } else if(this.role == UserRole.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_WRITER"), new SimpleGrantedAuthority("ROLE_READER"));
        } else if(this.role == UserRole.WRITER){
            return List.of(new SimpleGrantedAuthority("ROLE_WRITER"), new SimpleGrantedAuthority("ROLE_READER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_READER"));
        }
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}