package com.sousaarthur.blog.modules.user.service;

import com.sousaarthur.blog.modules.auth.model.Login;
import com.sousaarthur.blog.modules.auth.repository.LoginRepository;
import com.sousaarthur.blog.modules.user.model.User;
import com.sousaarthur.blog.modules.user.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUser {
    private final LoginRepository loginRepository;
    private final UserRepository userRepository;

    public AuthenticatedUser(LoginRepository loginRepository, UserRepository userRepository) {
        this.loginRepository = loginRepository;
        this.userRepository = userRepository;
    }

    public Login getLogin() {
        return (Login) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User getUser() {
        Login login = getLogin();
        return userRepository.findByLoginId(login.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
