package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unsa.etf.InventoryAndCatalogService.insertObject.ProductReview;
import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.responses.BadRequestResponseBody;
import com.unsa.etf.InventoryAndCatalogService.responses.PaginatedObjectResponse;
import com.unsa.etf.InventoryAndCatalogService.services.CategoryService;
import com.unsa.etf.InventoryAndCatalogService.services.ProductService;
import com.unsa.etf.InventoryAndCatalogService.services.SubcategoryService;
import com.unsa.etf.InventoryAndCatalogService.utils.InventoryTestMocks;
import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    private final String API_ROUTE = "/products";
    private final Category CATEGORY_MOCK = InventoryTestMocks.getCategoryMock("categoryName");
    private final Subcategory SUBCATEGORY_MOCK = InventoryTestMocks.getSubcategoryMock("subcategoryName", CATEGORY_MOCK);
    private final Product PRODUCT_MOCK = InventoryTestMocks.getProductMock("productName", "description", CATEGORY_MOCK, SUBCATEGORY_MOCK);

    @MockBean
    ProductService productService;

    @MockBean
    InventoryAndCatalogValidator inventoryAndCatalogValidator;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnEmptyList() throws Exception {
        given(productService.getAllProducts()).willReturn(Collections.emptyList());
        this.mockMvc.perform(get(API_ROUTE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.objectsList", hasSize(0)));
    }

    @Test
    public void shouldReturnOneProduct() throws Exception{
        given(productService.getProductById("id")).willReturn(PRODUCT_MOCK);
        mockMvc.perform(get(API_ROUTE + "/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.object.name", is(PRODUCT_MOCK.getName())));
    }

    @Test
    public void shouldNotReturnProduct() throws Exception{
        given(productService.getProductById("id")).willReturn(null);
        mockMvc.perform(get(API_ROUTE + "/{id}", "id"))
                .andExpect(jsonPath("$.error.message", is("Product Does Not Exist!")));
    }

    @Test
    public void shouldCreateNewProduct() throws Exception{
        given(productService.createOrUpdateProduct(PRODUCT_MOCK)).willReturn(PRODUCT_MOCK);
        given(inventoryAndCatalogValidator.isValid(PRODUCT_MOCK)).willReturn(true);
        mockMvc.perform(post(API_ROUTE)
                .content(asJsonString(PRODUCT_MOCK))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

    }

    @Test
    public void shouldNotCreateNewProductWhenValidationFails() throws Exception{
        given(inventoryAndCatalogValidator.isValid(PRODUCT_MOCK)).willReturn(false);
        given(inventoryAndCatalogValidator.determineConstraintViolation(PRODUCT_MOCK)).willReturn(new BadRequestResponseBody(null, "Error message"));
        mockMvc.perform(post(API_ROUTE)
                .content(asJsonString(PRODUCT_MOCK))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error.message", is("Error message")));

    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        given(productService.deleteProductById("id")).willReturn(true);
        mockMvc.perform(delete(API_ROUTE + "/{id}", "id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Product successfully deleted!")));
    }

    @Test
    public void shouldNotDeleteNonExistantProduct() throws Exception {
        mockMvc.perform(delete(API_ROUTE + "/{id}", "id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error.message", is("Product Does Not Exist!")));
    }

    @Test
    public void shouldReturnPageableListOfProducts() throws Exception{
        Product product2 = InventoryTestMocks.getProductMock("productName2", "description2", InventoryTestMocks.getCategoryMock("categoryName2"), InventoryTestMocks.getSubcategoryMock("subcategoryName2", InventoryTestMocks.getCategoryMock("categoryName2")));
        given(productService.readAndSortProducts(Pageable.ofSize(5))).willReturn(new PaginatedObjectResponse<>(200, List.of(PRODUCT_MOCK, product2), 5, 1, null));
        this.mockMvc.perform(get(API_ROUTE + "/search?size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.foundObjects").exists())
                .andExpect(jsonPath("$.foundObjects[0].name").exists())
                .andExpect(jsonPath("$.foundObjects[1].name").exists());
    }

    @Test
    public void shouldReturnUpdatedAndReviewedProduct() throws Exception{
        var product2 = Product.builder()
                .id("id")
                .name("product2")
                .description("description2")
                .category(CATEGORY_MOCK)
                .subcategory(SUBCATEGORY_MOCK)
                .reviewSum(1)
                .totalReviews(1)
                .build();
        given(productService.getProductById("id")).willReturn(PRODUCT_MOCK);
        given(productService.createOrUpdateProduct(PRODUCT_MOCK)).willReturn(product2);
        this.mockMvc.perform(put(API_ROUTE + "/reviewProduct/{id}", "id")
                .content(asJsonString(new ProductReview(1)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.object.totalReviews", is(1)))
                .andExpect(jsonPath("$.object.reviewSum", is(1)));

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
