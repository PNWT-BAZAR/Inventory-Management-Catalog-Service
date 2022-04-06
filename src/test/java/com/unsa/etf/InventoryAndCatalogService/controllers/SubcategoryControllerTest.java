package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.responses.BadRequestResponseBody;
import com.unsa.etf.InventoryAndCatalogService.responses.PaginatedObjectResponse;
import com.unsa.etf.InventoryAndCatalogService.services.CategoryService;
import com.unsa.etf.InventoryAndCatalogService.services.SubcategoryService;
import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubcategoryController.class)
public class SubcategoryControllerTest {
    private final String API_ROUTE = "/subcategories";

    @MockBean
    SubcategoryService subcategoryService;

    @MockBean
    InventoryAndCatalogValidator inventoryAndCatalogValidator;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnEmptyList() throws Exception {
        given(subcategoryService.getAllSubcategories()).willReturn(Collections.emptyList());
        this.mockMvc.perform(get(API_ROUTE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldReturnOneSubcategory() throws Exception{
        Category category = new Category("categoryName");
        Subcategory subcategory = new Subcategory("subcategoryName",category);
        given(subcategoryService.getSubcategoryById("id")).willReturn(subcategory);
        mockMvc.perform(get(API_ROUTE + "/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(subcategory.getName())));
    }

    @Test
    public void shouldNotReturnSubcategory() throws Exception{
        Category category = new Category("categoryName");
        Subcategory subcategory = new Subcategory("subcategoryName",category);
        given(subcategoryService.getSubcategoryById("id")).willReturn(null);
        mockMvc.perform(get(API_ROUTE + "/{id}", "id"))
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.message", is("Subcategory Does Not Exist!")));
    }

    @Test
    public void shouldCreateNewSubcategory() throws Exception{
        Category category = new Category("categoryName");
        Subcategory subcategory = new Subcategory("subcategoryName",category);
        given(subcategoryService.createOrUpdateSubcategory(subcategory)).willReturn(subcategory);
        given(inventoryAndCatalogValidator.isValid(subcategory)).willReturn(true);
        mockMvc.perform(post(API_ROUTE)
                .content(asJsonString(subcategory))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

    }

    @Test
    public void shouldNotCreateNewSubcategoryWhenValidationFails() throws Exception{
        Category category = new Category("categoryName");
        Subcategory subcategory = new Subcategory("subcategoryName",category);
        given(subcategoryService.createOrUpdateSubcategory(subcategory)).willReturn(subcategory);
        given(inventoryAndCatalogValidator.isValid(subcategory)).willReturn(false);
        given(inventoryAndCatalogValidator.determineConstraintViolation(subcategory)).willReturn(new BadRequestResponseBody(null, "Error message"));
        mockMvc.perform(post(API_ROUTE)
                .content(asJsonString(subcategory))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.message", is("Error message")));

    }

    @Test
    public void shouldDeleteSubcategory() throws Exception {
        given(subcategoryService.deleteSubcategoryById("id")).willReturn(true);
        mockMvc.perform(delete(API_ROUTE + "/{id}", "id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Subcategory successfully deleted!")));
    }

    @Test
    public void shouldNotDeleteNonExistantSubcategory() throws Exception {
        mockMvc.perform(delete(API_ROUTE + "/{id}", "id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("Subcategory Does Not Exist!")));
    }

    @Test
    public void shouldReturnPageableListOfSubcategories() throws Exception{
        Category category = new Category("categoryName");
        Subcategory subcategory = new Subcategory("subcategoryName",category);
        Category category2 = new Category("categoryName2");
        Subcategory subcategory2 = new Subcategory("subcategoryName2",category2);
        given(subcategoryService.readAndSortSubcategories(Pageable.ofSize(5))).willReturn(new PaginatedObjectResponse<>(List.of(subcategory, subcategory2), 5, 1));
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
