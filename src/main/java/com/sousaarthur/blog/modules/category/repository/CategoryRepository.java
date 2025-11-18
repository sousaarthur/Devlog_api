package com.sousaarthur.blog.modules.category.repository;

import com.sousaarthur.blog.modules.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String categoryName);

    @Query("SELECT c FROM Category c WHERE c.name LIKE %:nome%")
    List<Category> findByNamePartial(String nome);
//    List<Category> findByNomeContaining(String nome);
}
