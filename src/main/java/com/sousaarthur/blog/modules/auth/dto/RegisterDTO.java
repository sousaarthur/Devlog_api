package com.sousaarthur.blog.modules.auth.dto;

import com.sousaarthur.blog.modules.auth.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String login,
        @NotBlank
        String password,
        UserRole role
) {
}
