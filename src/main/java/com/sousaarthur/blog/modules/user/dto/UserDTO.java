package com.sousaarthur.blog.modules.user.dto;

import com.sousaarthur.blog.modules.user.model.User;
import lombok.Builder;

@Builder
public record UserDTO(
        Integer id,
        String name,
        String bio,
        String avatar,
        String linkedin,
        String github
) {
    public static UserDTO toDTO(User model){
        return UserDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .bio(model.getBio())
                .avatar(model.getAvatar())
                .linkedin(model.getLinkedin())
                .github(model.getGithub())
                .build();
    }
}
