package com.sousaarthur.blog.modules.user.repository;

import com.sousaarthur.blog.modules.auth.model.Login;
import com.sousaarthur.blog.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLoginId(Integer loginId);
    Page<User> findAll(Pageable page);
}