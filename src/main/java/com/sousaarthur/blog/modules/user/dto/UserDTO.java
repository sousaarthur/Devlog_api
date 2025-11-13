package com.sousaarthur.blog.modules.user.dto;

import com.sousaarthur.blog.modules.auth.model.UserRole;
import com.sousaarthur.blog.modules.user.model.User;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserDTO(
        Integer id,
        String name,
        String bio,
        String avatar,
        String linkedin,
        String github,
        String email,
        UserRole role,
        boolean status,
        LocalDate createdAt

) {
    public static UserDTO toDTO(User model){
        return UserDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .bio(model.getBio())
                .avatar(model.getAvatar())
                .linkedin(model.getLinkedin())
                .github(model.getGithub())
                .email(model.getLogin().getLogin())
                .role(model.getLogin().getRole())
                .status(model.getLogin().isActive())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
