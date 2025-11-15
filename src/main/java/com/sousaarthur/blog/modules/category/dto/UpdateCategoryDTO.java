package com.sousaarthur.blog.modules.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCategoryDTO(
        @NotBlank
        Integer id,
        String name
) {
}
