package com.sousaarthur.blog.modules.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateCategoryDTO(
        @NotBlank
        @Size(min = 4, max = 50)
        String name
) {

}
