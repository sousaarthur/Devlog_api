package com.sousaarthur.blog.modules.category.service;

import com.sousaarthur.blog.exception.CategoryNotFoundException;
import com.sousaarthur.blog.exception.IllegalCategoryCreateException;
import com.sousaarthur.blog.modules.category.dto.CategoryResponseDTO;
import com.sousaarthur.blog.modules.category.dto.UpdateCategoryDTO;
import com.sousaarthur.blog.modules.category.model.Category;
import com.sousaarthur.blog.modules.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.InvalidNameException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private MessageSource messageSource;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, MessageSource messageSource) {
        this.categoryRepository = categoryRepository;
        this.messageSource = messageSource;
    }

    public List<CategoryResponseDTO> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponseDTO::toDTO)
                .collect(Collectors.toList());
    }
    public List<CategoryResponseDTO> getCategoriesByName(String name){
        return categoryRepository.findByNamePartial(name)
                .stream()
                .map(CategoryResponseDTO::toDTO)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategory(Integer id) {
        var category = categoryRepository.findById(id);
        if(!category.isPresent()) throw new CategoryNotFoundException();
        return CategoryResponseDTO.toDTO(category.get());
    }

    public CategoryResponseDTO getCategory(String categoryName) {
        var category = categoryRepository.findByName(categoryName);
        if(!category.isPresent()) throw new CategoryNotFoundException();
        return CategoryResponseDTO.toDTO(category.get());
    }

    @Transactional
    public CategoryResponseDTO createCategory(String categoryName) throws InvalidNameException {
        if (isCategoryExists(categoryName) == true){
            throw new IllegalCategoryCreateException(
                    messageSource.getMessage("illegal.category.create", null, LocaleContextHolder.getLocale())
            );
        }

        if (categoryName == null || categoryName.isEmpty()) {
            throw new InvalidNameException("Nome de categoria invalido");
        }
        var slug = generateSlug(categoryName);
        var newCategory = new Category();
        newCategory.setName(categoryName);
        newCategory.setActive(true);
        newCategory.setSlug(slug);

        categoryRepository.save(newCategory);
        return CategoryResponseDTO.toDTO(newCategory);
    }

    @Transactional
    public CategoryResponseDTO updateCategory(UpdateCategoryDTO dto) throws InvalidNameException {
        var categoryTarget = categoryRepository.findById(dto.id()).get();

        if (!dto.name().isEmpty()){
            categoryTarget.setName(dto.name());
        }
        if (!dto.slug().isEmpty()){
            categoryTarget.setSlug(dto.slug());
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
            var categoryTarget =  categoryRepository.findByName(name).get();

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
        var category = categoryRepository.findByName(name);
        if (category.isPresent()) return true;
        return false;
    }
}
