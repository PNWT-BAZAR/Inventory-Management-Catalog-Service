package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.services.SubcategoryService;
import com.unsa.etf.InventoryAndCatalogService.responses.BadRequestResponseBody;
import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
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
    public ResponseEntity<?> getSubcategoryById(@PathVariable String id) {
        Subcategory subcategory = subcategoryService.getSubcategoryById(id);
        if (subcategory == null) {
            return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Subcategory Does Not Exist!"));
        }
        return ResponseEntity.status(200).body(subcategory);
    }

    @PostMapping
    public ResponseEntity<?> createNewSubcategory(@RequestBody Subcategory subcategory) {
        if (inventoryAndCatalogValidator.isValid(subcategory)) {
            Subcategory newSubcategory = subcategoryService.createOrUpdateSubcategory(subcategory);
            return ResponseEntity.status(200).body(subcategory);
        }
        return ResponseEntity.status(409).body(inventoryAndCatalogValidator.determineConstraintViolation(subcategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubcategory(@PathVariable String id) {
        boolean deleted = subcategoryService.deleteSubcategoryById(id);
        if (deleted)
            return ResponseEntity.status(200).body("Subcategory successfully deleted!");
        return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Subcategory Does Not Exist!"));
    }

    @PutMapping
    public ResponseEntity<?> updateSubcategory(@RequestBody Subcategory subcategory) {
        if (inventoryAndCatalogValidator.isValid(subcategory)) {
            Subcategory updatedSubcategory = subcategoryService.createOrUpdateSubcategory(subcategory);
            return ResponseEntity.status(200).body(updatedSubcategory);
        }
        return ResponseEntity.status(409).body(inventoryAndCatalogValidator.determineConstraintViolation(subcategory));
    }

    //Sorting and Pagination
    @GetMapping("/search")
    public ResponseEntity<?> readSubcategories (Pageable pageable){
        try{
            return ResponseEntity.status(200).body(subcategoryService.readAndSortSubcategories(pageable));
        }catch (PropertyReferenceException e){
            return ResponseEntity.status(409).body(new BadRequestResponseBody (BadRequestResponseBody.ErrorCode.NOT_FOUND, e.getMessage()));
        }
    }
}
