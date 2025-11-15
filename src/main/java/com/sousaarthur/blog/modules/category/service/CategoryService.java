package com.sousaarthur.blog.modules.category.service;

import com.sousaarthur.blog.exception.CategoryNotFoundException;
import com.sousaarthur.blog.exception.IllegalCategoryCreateException;
import com.sousaarthur.blog.modules.category.dto.UpdateCategoryDTO;
import com.sousaarthur.blog.modules.category.model.Category;
import com.sousaarthur.blog.modules.category.dto.CategoryResponseDTO;
import com.sousaarthur.blog.modules.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.InvalidNameException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public List<CategoryResponseDTO> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(c -> CategoryResponseDTO.toDTO(c))
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategory(Integer id) {
        var category = categoryRepository.findById(id);
        if(!category.isPresent()) throw new CategoryNotFoundException();
        return CategoryResponseDTO.toDTO(category.get());
    }

    public CategoryResponseDTO getCategory(String categoryName) {
        var category = categoryRepository.findByNameCategory(categoryName);
        if(!category.isPresent()) throw new CategoryNotFoundException();
        return CategoryResponseDTO.toDTO(category.get());
    }

    @Transactional
    public CategoryResponseDTO createCategory(String categoryName) throws InvalidNameException {
        if (!isCategoryExists(categoryName) == true){
            throw new IllegalCategoryCreateException();
        }

        if (categoryName == null && categoryName.isEmpty()) {
            throw new InvalidNameException("Nome de categoria invalido");
        }

        var newCategory = new Category();
        newCategory.setName(categoryName);
        newCategory.setSlug(generateSlug(categoryName));

        categoryRepository.save(newCategory);
        return CategoryResponseDTO.toDTO(newCategory);
    }

    @Transactional
    public CategoryResponseDTO updateCategory(UpdateCategoryDTO dto) throws InvalidNameException {
        var categoryTarget = categoryRepository.findById(dto.id()).get();
        if (!isCategoryExists(dto.name()) == true) {
            throw new IllegalCategoryCreateException();
        }
        if (dto.name().isEmpty()){
            categoryTarget.setName(dto.name());
        }
        categoryRepository.save(categoryTarget);
        return CategoryResponseDTO.toDTO(categoryTarget);
    }

    public boolean deactivateCategory(Integer id){
        try {
            var categoryTarget =  categoryRepository.findById(id).get();

            if (!categoryTarget.isActive()) {
                return false;
            }
            categoryTarget.setActive(false);
            categoryRepository.save(categoryTarget);
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao desativar categoria " + e);
        }
    }

    public boolean deactivateCategory(String name){
        try {
            var categoryTarget =  categoryRepository.findByNameCategory(name).get();

            if (!categoryTarget.isActive()) {
                return false;
            }

            categoryTarget.setActive(false);
            categoryRepository.save(categoryTarget);
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao desativar categoria " + e);
        }
    }

    /* Não foi adicionado um metodo para excluir, pois pensando na logica de relacioanemnto, isso acarretaria em problemas de atomicidade no banco */
    // TODO: Gerar slug, considerando questões como categorias com nomes compostos, com valores numericos e etc
    protected String generateSlug(String categoryName){
        categoryName = categoryName.replaceAll("[^a-zA-Z0-9]", "");
        categoryName = categoryName.replaceAll("\\s+", "-");
        return categoryName.toLowerCase();
    }

    // TODO: Garantir que não tenham categorias sobrepostas
    protected boolean isCategoryExists(String name){
        var category = categoryRepository.findByNameCategory(name);
        if (category.isPresent()) return true;
        return false;
    }
}
