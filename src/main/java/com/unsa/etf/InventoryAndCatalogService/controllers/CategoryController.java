package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.services.CategoryService;
import com.unsa.etf.InventoryAndCatalogService.responses.BadRequestResponseBody;
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
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable String id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Category Does Not Exist!"));
        }
        return ResponseEntity.status(200).body(category);
    }

    @PostMapping
    public ResponseEntity<?> createNewCategory(@RequestBody Category category) {
        if (inventoryAndCatalogValidator.isValid(category)) {
            Category newCategory = categoryService.createOrUpdateCategory(category);
            return ResponseEntity.status(200).body(newCategory);
        }else
            return ResponseEntity.status(409).body(inventoryAndCatalogValidator.determineConstraintViolation(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        boolean deleted = categoryService.deleteCategoryById(id);
        if (deleted)
            return ResponseEntity.status(200).body("Category Successfully Deleted!");
        return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Category Does Not Exist!"));
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        if (inventoryAndCatalogValidator.isValid(category)) {
            Category updatedCategory = categoryService.createOrUpdateCategory(category);
            return ResponseEntity.status(200).body(updatedCategory);
        }
        return ResponseEntity.status(409).body(inventoryAndCatalogValidator.determineConstraintViolation(category));

    }

    //Sorting and Pagination
    @GetMapping("/search")
    public ResponseEntity<?> readCategories (Pageable pageable){
        try{
            return ResponseEntity.status(200).body(categoryService.readAndSortCategories(pageable));
        }catch (PropertyReferenceException e){
            return ResponseEntity.status(409).body(new BadRequestResponseBody (BadRequestResponseBody.ErrorCode.NOT_FOUND, e.getMessage()));
        }
    }

}
