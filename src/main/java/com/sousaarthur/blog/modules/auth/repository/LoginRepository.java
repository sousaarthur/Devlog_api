package com.sousaarthur.blog.modules.auth.repository;

import com.sousaarthur.blog.modules.auth.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginRepository extends JpaRepository<Login, String> {
    UserDetails findByLogin(String login);
}
