package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.services.SubcategoryService;
import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subcategories")
public class SubcategoryController {
    private final SubcategoryService subcategoryService;
    private final InventoryAndCatalogValidator inventoryAndCatalogValidator;

    @Autowired
    public SubcategoryController(SubcategoryService subcategoryService, InventoryAndCatalogValidator inventoryAndCatalogValidator) {
        this.subcategoryService = subcategoryService;
        this.inventoryAndCatalogValidator = inventoryAndCatalogValidator;
    }

    @GetMapping
    public List<Subcategory> getAllSubcategories() {
        return subcategoryService.getAllSubcategories();
    }

    @GetMapping("/{id}")
    public Subcategory getSubcategoryById(@PathVariable String id) {
        return subcategoryService.getSubcategoryById(id);
    }

    @PostMapping
    public ResponseEntity<?> createNewSubcategory(@RequestBody Subcategory subcategory) {
        if (inventoryAndCatalogValidator.isValid(subcategory))
            return ResponseEntity.status(200).body(subcategoryService.createOrUpdateSubcategory(subcategory));
        else
            return ResponseEntity.status(409).body(inventoryAndCatalogValidator.determineConstraintViolation(subcategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubcategory(@PathVariable String id) {
        boolean deleted = subcategoryService.deleteSubcategoryById(id);
        if (deleted)
            return ResponseEntity.status(200).body("Object successfully deleted");
        return ResponseEntity.status(409).body("Object with id: " + id + " does not exist");
    }

    @PutMapping
    public ResponseEntity<?> updateSubcategory(@RequestBody Subcategory subcategory) {
        if (!inventoryAndCatalogValidator.isValid(subcategory))
            return ResponseEntity.status(409).body(inventoryAndCatalogValidator.determineConstraintViolation(subcategory));

        Subcategory newSubcategory = subcategoryService.createOrUpdateSubcategory(subcategory);
        return ResponseEntity.status(200).body("Successfully updated product: " + newSubcategory);
    }
}
