package com.sousaarthur.blog.modules.category.dto;

import com.sousaarthur.blog.modules.category.model.Category;
import lombok.Builder;

@Builder
public record CategoryResponseDTO(
        Integer id,
        String name,
        String slug,
        boolean active
) {
    public static CategoryResponseDTO toDTO(Category model) {
        return CategoryResponseDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .active(model.isActive())
                .slug(model.getSlug())
                .build();
    }
}
