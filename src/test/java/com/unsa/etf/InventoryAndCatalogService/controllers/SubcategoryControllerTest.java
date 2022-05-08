//package com.unsa.etf.InventoryAndCatalogService.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.unsa.etf.InventoryAndCatalogService.model.Category;
//import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
//import com.unsa.etf.InventoryAndCatalogService.responses.BadRequestResponseBody;
//import com.unsa.etf.InventoryAndCatalogService.responses.PaginatedObjectResponse;
//import com.unsa.etf.InventoryAndCatalogService.services.CategoryService;
//import com.unsa.etf.InventoryAndCatalogService.services.SubcategoryService;
//import com.unsa.etf.InventoryAndCatalogService.utils.InventoryTestMocks;
//import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.hamcrest.Matchers.*;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(SubcategoryController.class)
//public class SubcategoryControllerTest {
//    private final String API_ROUTE = "/subcategories";
//    private final Category CATEGORY_MOCK = InventoryTestMocks.getCategoryMock("categoryName");
//    private final Subcategory SUBCATEGORY_MOCK = InventoryTestMocks.getSubcategoryMock("subcategoryName", CATEGORY_MOCK);
//
//    @MockBean
//    SubcategoryService subcategoryService;
//
//    @MockBean
//    InventoryAndCatalogValidator inventoryAndCatalogValidator;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void shouldReturnEmptyList() throws Exception {
//        given(subcategoryService.getAllSubcategories()).willReturn(Collections.emptyList());
//        this.mockMvc.perform(get(API_ROUTE))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.objectsList", hasSize(0)));
//    }
//
//    @Test
//    public void shouldReturnOneSubcategory() throws Exception{
//        given(subcategoryService.getSubcategoryById("id")).willReturn(SUBCATEGORY_MOCK);
//        mockMvc.perform(get(API_ROUTE + "/{id}", "id"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.object.name", is(SUBCATEGORY_MOCK.getName())));
//    }
//
//    @Test
//    public void shouldNotReturnSubcategory() throws Exception{
//        given(subcategoryService.getSubcategoryById("id")).willReturn(null);
//        mockMvc.perform(get(API_ROUTE + "/{id}", "id"))
//                .andExpect(jsonPath("$.error.message", is("Subcategory Does Not Exist!")));
//    }
//
//    @Test
//    public void shouldCreateNewSubcategory() throws Exception{
//        given(subcategoryService.createOrUpdateSubcategory(SUBCATEGORY_MOCK)).willReturn(SUBCATEGORY_MOCK);
//        given(inventoryAndCatalogValidator.isValid(SUBCATEGORY_MOCK)).willReturn(true);
//        mockMvc.perform(post(API_ROUTE)
//                .content(asJsonString(SUBCATEGORY_MOCK))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").exists());
//
//    }
//
//    @Test
//    public void shouldNotCreateNewSubcategoryWhenValidationFails() throws Exception{
//        given(subcategoryService.createOrUpdateSubcategory(SUBCATEGORY_MOCK)).willReturn(SUBCATEGORY_MOCK);
//        given(inventoryAndCatalogValidator.isValid(SUBCATEGORY_MOCK)).willReturn(false);
//        given(inventoryAndCatalogValidator.determineConstraintViolation(SUBCATEGORY_MOCK)).willReturn(new BadRequestResponseBody(null, "Error message"));
//        mockMvc.perform(post(API_ROUTE)
//                .content(asJsonString(SUBCATEGORY_MOCK))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.error.message", is("Error message")));
//
//    }
//
//    @Test
//    public void shouldDeleteSubcategory() throws Exception {
//        given(subcategoryService.deleteSubcategoryById("id")).willReturn(true);
//        mockMvc.perform(delete(API_ROUTE + "/{id}", "id")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message", is("Subcategory successfully deleted!")));
//    }
//
//    @Test
//    public void shouldNotDeleteNonExistantSubcategory() throws Exception {
//        mockMvc.perform(delete(API_ROUTE + "/{id}", "id")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.error.message", is("Subcategory Does Not Exist!")));
//    }
//
//    @Test
//    public void shouldReturnPageableListOfSubcategories() throws Exception{
//        Category category2 = InventoryTestMocks.getCategoryMock("categoryName2");
//        Subcategory subcategory2 = InventoryTestMocks.getSubcategoryMock("subcategoryName2", category2);
//        given(subcategoryService.readAndSortSubcategories(Pageable.ofSize(5))).willReturn(new PaginatedObjectResponse<>(200, List.of(SUBCATEGORY_MOCK, subcategory2), 5, 1, null));
//        this.mockMvc.perform(get(API_ROUTE + "/search?size=5"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.foundObjects").exists())
//                .andExpect(jsonPath("$.foundObjects[0].name").exists())
//                .andExpect(jsonPath("$.foundObjects[1].name").exists());
//    }
//
//
//    public static String asJsonString(final Object obj) {
//        try {
//            System.out.println((new ObjectMapper().writeValueAsString(obj)));
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
