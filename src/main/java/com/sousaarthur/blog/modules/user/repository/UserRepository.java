package com.sousaarthur.blog.modules.user.repository;

import com.sousaarthur.blog.modules.auth.model.Login;
import com.sousaarthur.blog.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLoginId(Integer loginId);
}