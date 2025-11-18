package com.sousaarthur.blog.modules.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCategoryDTO(
        @NotBlank
        Integer id,
        String name,
        String slug
) {
    public UpdateCategoryDTO(@NotBlank Integer id, String name, String slug) {
        this.id = id;

        if (name == null || name.isEmpty()) {
            this.name = "";
        } else {
            this.name = name;
        }
        if (slug == null || slug.isEmpty()) {
            this.slug = "";
        } else {
            this.slug = slug;
        }
    }
}
