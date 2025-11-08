package com.sousaarthur.blog.modules.user.dto;

import lombok.Builder;

@Builder
public record ChangePasswordDTO(
        String currentPassword,
        String newPassword
) { }
