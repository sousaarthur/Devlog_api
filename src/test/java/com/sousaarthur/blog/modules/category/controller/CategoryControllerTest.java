package com.sousaarthur.blog.modules.category.controller;

import com.sousaarthur.blog.config.security.TokenService;
import com.sousaarthur.blog.modules.auth.repository.LoginRepository;
import com.sousaarthur.blog.modules.category.dto.CategoryResponseDTO;
import com.sousaarthur.blog.modules.category.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService  categoryService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private LoginRepository loginRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllCategories() {
    }

    @Test
    @WithMockUser
    void getCategory_should_it_sucess() throws Exception {
        var category = CategoryResponseDTO.builder()
                .name("teste")
                .build();

        when(categoryService.getCategory(anyString())).thenReturn(category);

        mockMvc.perform(get("/api/category")
                .param("name", "teste"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name").value("teste"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createCategory() throws Exception {
        var category = CategoryResponseDTO.builder()
                .name("FrontEnd")
                .build();

        when(categoryService.createCategory(any())).thenReturn(category);

        mockMvc.perform(post("/api/category/create")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"FrontEnd\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("FrontEnd"));
    }

    @Test
    void updateCategory() {
    }

    @Test
    void disableCategory() {
    }
}