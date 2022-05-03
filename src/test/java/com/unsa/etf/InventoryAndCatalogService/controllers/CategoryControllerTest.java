package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.repositories.CategoryRepository;
import com.unsa.etf.InventoryAndCatalogService.responses.BadRequestResponseBody;
import com.unsa.etf.InventoryAndCatalogService.responses.PaginatedObjectResponse;
import com.unsa.etf.InventoryAndCatalogService.services.CategoryService;
import com.unsa.etf.InventoryAndCatalogService.utils.InventoryTestMocks;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    private final String API_ROUTE = "/categories";
    private final Category CATEGORY_MOCK = InventoryTestMocks.getCategoryMock("categoryName");

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
                .andExpect(jsonPath("$.objectsList", hasSize(0)));
    }

    @Test
    public void shouldReturnOneCategory() throws Exception{
        given(categoryService.getCategoryById("id")).willReturn(CATEGORY_MOCK);
        mockMvc.perform(get(API_ROUTE + "/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.object.name", is(CATEGORY_MOCK.getName())));
    }

    @Test
    public void shouldNotReturnCategory() throws Exception{
        given(categoryService.getCategoryById("id")).willReturn(null);
        mockMvc.perform(get(API_ROUTE + "/{id}", "id"))
                .andExpect(jsonPath("$.error.message", is("Category Does Not Exist!")));
    }

    @Test
    public void shouldCreateNewCategory() throws Exception{
        given(categoryService.createOrUpdateCategory(CATEGORY_MOCK)).willReturn(CATEGORY_MOCK);
        given(inventoryAndCatalogValidator.isValid(CATEGORY_MOCK)).willReturn(true);
        mockMvc.perform(post(API_ROUTE)
                .content(asJsonString(CATEGORY_MOCK))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

    }

    @Test
    public void shouldNotCreateNewCategoryWhenValidationFails() throws Exception{
        given(categoryService.createOrUpdateCategory(CATEGORY_MOCK)).willReturn(CATEGORY_MOCK);
        given(inventoryAndCatalogValidator.isValid(CATEGORY_MOCK)).willReturn(false);
        given(inventoryAndCatalogValidator.determineConstraintViolation(CATEGORY_MOCK)).willReturn(new BadRequestResponseBody(null, "Error message"));
        mockMvc.perform(post(API_ROUTE)
                .content(asJsonString(CATEGORY_MOCK))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error.message", is("Error message")));

    }

    @Test
    public void shouldDeleteCategory() throws Exception {
        given(categoryService.deleteCategoryById("id")).willReturn(true);
        mockMvc.perform(delete(API_ROUTE + "/{id}", "id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Category Successfully Deleted!")));
    }

    @Test
    public void shouldNotDeleteNonExistantCategory() throws Exception {
        mockMvc.perform(delete(API_ROUTE + "/{id}", "id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error.message", is("Category Does Not Exist!")));
    }

    @Test
    public void shouldReturnPageableListOfCategories() throws Exception{
        Category category2 = InventoryTestMocks.getCategoryMock("categoryName2");
        given(categoryService.readAndSortCategories(Pageable.ofSize(5))).willReturn(new PaginatedObjectResponse<>(200, List.of(CATEGORY_MOCK, category2), 5, 1, null));
        this.mockMvc.perform(get(API_ROUTE + "/search?size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.foundObjects").exists())
                .andExpect(jsonPath("$.foundObjects[0].name").exists())
                .andExpect(jsonPath("$.foundObjects[1].name").exists());
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
