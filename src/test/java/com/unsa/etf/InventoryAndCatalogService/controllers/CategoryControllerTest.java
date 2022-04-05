package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.repositories.CategoryRepository;
import com.unsa.etf.InventoryAndCatalogService.responses.BadRequestResponseBody;
import com.unsa.etf.InventoryAndCatalogService.services.CategoryService;
import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
import org.apiguardian.api.API;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    private final String API_ROUTE = "/categories";

    @MockBean
    CategoryService categoryService;

    @MockBean
    InventoryAndCatalogValidator inventoryAndCatalogValidator;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnEmptyList() throws Exception {
        given(categoryService.getAllCategories()).willReturn(Collections.emptyList());
        this.mockMvc.perform(get(API_ROUTE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldReturnOneCategory() throws Exception{
        Category category = new Category("name");
        given(categoryService.getCategoryById("id")).willReturn(category);
        mockMvc.perform(get(API_ROUTE + "/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(category.getName())));
    }

    @Test
    public void shouldNotReturnCategory() throws Exception{
        Category category = new Category("name");
        given(categoryService.getCategoryById("id")).willReturn(null);
        mockMvc.perform(get(API_ROUTE + "/{id}", "id"))
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.message", is("Category Does Not Exist!")));
    }

    @Test
    public void shouldCreateNewCategory() throws Exception{
        Category category = new Category("name");
        given(categoryService.createOrUpdateCategory(category)).willReturn(category);
        given(inventoryAndCatalogValidator.isValid(category)).willReturn(true);
        mockMvc.perform(post(API_ROUTE)
                .content(asJsonString(category))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

    }

    @Test
    public void shouldNotCreateNewCategoryWhenValidationFails() throws Exception{
        Category category = new Category("name");
        given(categoryService.createOrUpdateCategory(category)).willReturn(category);
        given(inventoryAndCatalogValidator.isValid(category)).willReturn(false);
        given(inventoryAndCatalogValidator.determineConstraintViolation(category)).willReturn(new BadRequestResponseBody(null, "Error message"));
        mockMvc.perform(post(API_ROUTE)
                .content(asJsonString(category))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.message", is("Error message")));

    }

    @Test
    public void shouldDeleteCategory() throws Exception {
        given(categoryService.deleteCategoryById("id")).willReturn(true);
        mockMvc.perform(delete(API_ROUTE + "/{id}", "id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Category Successfully Deleted!")));
    }

    @Test
    public void shouldNotDeleteNonExistantCategory() throws Exception {
        mockMvc.perform(delete(API_ROUTE + "/{id}", "id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("Category Does Not Exist!")));
    }


    public static String asJsonString(final Object obj) {
        try {
            System.out.println((new ObjectMapper().writeValueAsString(obj)));
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
