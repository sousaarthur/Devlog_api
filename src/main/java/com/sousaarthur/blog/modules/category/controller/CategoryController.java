package com.sousaarthur.blog.modules.category.controller;

import com.sousaarthur.blog.modules.category.dto.CategoryResponseDTO;
import com.sousaarthur.blog.modules.category.dto.UpdateCategoryDTO;
import com.sousaarthur.blog.modules.category.dto.createCategoryDTO;
import com.sousaarthur.blog.modules.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.InvalidNameException;
import java.util.List;

@Controller
@RequestMapping("api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        var categories = categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    /**
     * Recebe o nome ou o id da categoria e o retorna*/
    @GetMapping
    public ResponseEntity<CategoryResponseDTO> getCategory(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "name", required = false, defaultValue = "") String name
    ){
        CategoryResponseDTO category;

        if (!name.isEmpty()){
            category = categoryService.getCategory(name);
        } else if (id != null){
            category = categoryService.getCategory(id);
        }  else {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

//    @RequestMapping("/{id}")
//    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable("id") int id){
//        var category = categoryService.getCategory(id);
//        return ResponseEntity.status(HttpStatus.OK).body(category);
//    }
//
//    @RequestMapping("name/{name}")
//    public ResponseEntity<CategoryResponseDTO> getCategoryByName(@PathVariable("name") String name){
//        var category = categoryService.getCategory(name);
//        return ResponseEntity.status(HttpStatus.OK).body(category);
//    }

    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDTO> createCategory(createCategoryDTO dto) throws InvalidNameException {
        var category = categoryService.createCategory(dto.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@RequestBody UpdateCategoryDTO dto) throws InvalidNameException {
        var category = categoryService.updateCategory(dto);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @GetMapping("/disable/{id}")
    public ResponseEntity disableCategory(@PathVariable("id") Integer id){
        var deactivate = categoryService.deactivateCategory(id);
        return  ResponseEntity.status(HttpStatus.OK).body(deactivate);
    }
}
