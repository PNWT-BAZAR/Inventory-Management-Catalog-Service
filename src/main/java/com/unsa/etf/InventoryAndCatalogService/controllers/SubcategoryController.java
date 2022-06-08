package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.responses.*;
import com.unsa.etf.InventoryAndCatalogService.services.SubcategoryService;
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
    public ObjectListResponse<Subcategory> getAllSubcategories() {
        return new ObjectListResponse<>(200, subcategoryService.getAllSubcategories(), null);
    }

    @GetMapping("/{id}")
    public ObjectResponse<Subcategory> getSubcategoryById(@PathVariable String id) {
        Subcategory subcategory = subcategoryService.getSubcategoryById(id);
        if (subcategory == null) {
            return new ObjectResponse<>(409, null, new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Subcategory Does Not Exist!"));
        }
        return new ObjectResponse<>(200, subcategory, null);
    }

    @PostMapping
    public ObjectResponse<Subcategory> createNewSubcategory(@RequestBody Subcategory subcategory) {
        if (inventoryAndCatalogValidator.isValid(subcategory)) {
            Subcategory newSubcategory = subcategoryService.createOrUpdateSubcategory(subcategory);
            return new ObjectResponse<>(200, subcategory, null);
        }
        return new ObjectResponse<>(409, null, inventoryAndCatalogValidator.determineConstraintViolation(subcategory));
    }

    @DeleteMapping("/{id}")
    public ObjectDeletionResponse deleteSubcategory(@PathVariable String id) {
        boolean deleted = subcategoryService.deleteSubcategoryById(id);
        if (deleted)
            return new ObjectDeletionResponse(200, "Subcategory successfully deleted!", null);
        return new ObjectDeletionResponse(409, "An error has occurred!", new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Subcategory Does Not Exist!"));
    }

    @PutMapping
    public ObjectResponse<Subcategory> updateSubcategory(@RequestBody Subcategory subcategory) {
        if (inventoryAndCatalogValidator.isValid(subcategory)) {
            Subcategory updatedSubcategory = subcategoryService.createOrUpdateSubcategory(subcategory);
            return new ObjectResponse<>(200, updatedSubcategory, null);
        }
        return new ObjectResponse<>(409, null, inventoryAndCatalogValidator.determineConstraintViolation(subcategory));
    }

    //Sorting and Pagination
    @GetMapping("/search")
    public PaginatedObjectResponse<Subcategory> readSubcategories (Pageable pageable){
        try{
            return subcategoryService.readAndSortSubcategories(pageable);
        }catch (PropertyReferenceException e){
            return PaginatedObjectResponse.<Subcategory>builder().statusCode(409).error(new BadRequestResponseBody (BadRequestResponseBody.ErrorCode.NOT_FOUND, e.getMessage())).build();
        }
    }

    @GetMapping("/searchByCategory")
    public ObjectListResponse<Subcategory> getSubcategoriesByCategory(@RequestParam(value = "category", required = true) String categoryName){
        var subcategories = subcategoryService.getSubcategoriesByCategory(categoryName);
        return new ObjectListResponse<>(200, subcategories, null);
    }
}
