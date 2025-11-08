package com.sousaarthur.blog.modules.cloudinary.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record CloudinaryDTO(
        String url,
        String createdAt
) {
    public CloudinaryDTO(String url, String createdAt){
        this.url = url;
        this.createdAt = createdAt;
    }
}
