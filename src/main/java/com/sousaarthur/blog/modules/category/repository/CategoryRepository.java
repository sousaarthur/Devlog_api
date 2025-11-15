package com.sousaarthur.blog.modules.category.repository;

import com.sousaarthur.blog.modules.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByNameCategory(String categoryName);
}
