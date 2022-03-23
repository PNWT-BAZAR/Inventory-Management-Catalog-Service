package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.services.CategoryService;
import com.unsa.etf.InventoryAndCatalogService.services.ProductService;
import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Category getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public ResponseEntity<?> createNewCategory(@RequestBody Category category) {
        if (inventoryAndCatalogValidator.isValid(category))
            return ResponseEntity.status(200).body(categoryService.createOrUpdateCategory(category));
        else
            return ResponseEntity.status(409).body(inventoryAndCatalogValidator.determineConstraintViolation(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        boolean deleted = categoryService.deleteCategoryById(id);
        if (deleted)
            return ResponseEntity.status(200).body("Object successfully deleted");
        return ResponseEntity.status(409).body("Object with id: " + id + " does not exist");
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        if (!inventoryAndCatalogValidator.isValid(category))
            return ResponseEntity.status(409).body(inventoryAndCatalogValidator.determineConstraintViolation(category));

        Category newCategory = categoryService.createOrUpdateCategory(category);
        return ResponseEntity.status(200).body("Successfully updated product: " + newCategory);
    }

}
