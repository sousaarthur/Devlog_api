package com.sousaarthur.blog.modules.auth.dto;

import com.sousaarthur.blog.modules.auth.model.UserRole;

public record RegisterDTO(
        String login,
        String password,
        UserRole role
) {
}
