package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.responses.*;
import com.unsa.etf.InventoryAndCatalogService.services.CategoryService;
import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final InventoryAndCatalogValidator inventoryAndCatalogValidator;

    @Autowired
    public CategoryController(CategoryService categoryService, InventoryAndCatalogValidator inventoryAndCatalogValidator) {
        this.categoryService = categoryService;
        this.inventoryAndCatalogValidator = inventoryAndCatalogValidator;
    }

    @GetMapping
    public ObjectListResponse<Category> getAllCategories() {
        return new ObjectListResponse<>(200, categoryService.getAllCategories(), null);
    }

    @GetMapping("/{id}")
    public ObjectResponse<Category> getCategoryById(@PathVariable String id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return new ObjectResponse<>(409, null, new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Category Does Not Exist!"));
        }
        return new ObjectResponse<>(200, category, null);
    }

    @PostMapping
    public ObjectResponse<Category> createNewCategory(@RequestBody Category category) {
        if (inventoryAndCatalogValidator.isValid(category)) {
            Category newCategory = categoryService.createOrUpdateCategory(category);
            return new ObjectResponse<>(200, newCategory, null);
        }else
            return new ObjectResponse<>(409, null, inventoryAndCatalogValidator.determineConstraintViolation(category));
    }

    @DeleteMapping("/{id}")
    public ObjectDeletionResponse deleteCategory(@PathVariable String id) {
        boolean deleted = categoryService.deleteCategoryById(id);
        if (deleted)
            return new ObjectDeletionResponse(200, "Category Successfully Deleted!", null);
        return new ObjectDeletionResponse(409, "An error has occurred!", new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Category Does Not Exist!"));
    }

    @PutMapping
    public ObjectResponse<Category> updateCategory(@RequestBody Category category) {
        if (inventoryAndCatalogValidator.isValid(category)) {
            Category updatedCategory = categoryService.createOrUpdateCategory(category);
            return new ObjectResponse<>(200, updatedCategory, null);
        }
        return new ObjectResponse<>(409, null, inventoryAndCatalogValidator.determineConstraintViolation(category));

    }

    //Sorting and Pagination
    @GetMapping("/search")
    public PaginatedObjectResponse<Category> readCategories (Pageable pageable){
        try{
            return categoryService.readAndSortCategories(pageable);
        }catch (PropertyReferenceException e){
            return PaginatedObjectResponse.<Category>builder().statusCode(409).error(new BadRequestResponseBody (BadRequestResponseBody.ErrorCode.NOT_FOUND, e.getMessage())).build();
        }
    }

}
