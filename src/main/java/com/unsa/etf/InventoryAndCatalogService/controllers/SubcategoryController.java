package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.responses.*;
import com.unsa.etf.InventoryAndCatalogService.services.CategoryService;
import com.unsa.etf.InventoryAndCatalogService.services.SubcategoryService;
import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subcategories")
public class SubcategoryController {
    private final SubcategoryService subcategoryService;
    private final CategoryService categoryService;
    private final InventoryAndCatalogValidator inventoryAndCatalogValidator;

    @GetMapping
    public ObjectListResponse<Subcategory> getSubcategories(){
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
//    @GetMapping("/search")
//    public PaginatedObjectResponse<Subcategory> readSubcategories (Pageable pageable){
//        try{
//            return subcategoryService.readAndSortSubcategories(pageable);
//        }catch (PropertyReferenceException e){
//            return PaginatedObjectResponse.<Subcategory>builder().statusCode(409).error(new BadRequestResponseBody (BadRequestResponseBody.ErrorCode.NOT_FOUND, e.getMessage())).build();
//        }
//    }

    @GetMapping("/searchByCategory")
    public ObjectListResponse<Subcategory> getSubcategoriesByCategory(@RequestParam(value = "category", required = true) String categoryName){
        var subcategories = subcategoryService.getSubcategoriesByCategory(categoryName);
        return new ObjectListResponse<>(200, subcategories, null);
    }

//    @GetMapping("/search")
//    public ObjectListResponse<Subcategory> searchSubcategoriesByName (@RequestParam String searchInput){
//        try{
//            return new ObjectListResponse<>(200, subcategoryService.searchSubcategoriesByName(searchInput), null);
//        }catch (Exception e){
//            return ObjectListResponse.<Subcategory>builder().statusCode(409).error(new BadRequestResponseBody (BadRequestResponseBody.ErrorCode.NOT_FOUND, e.getMessage())).build();
//        }
//    }

    @GetMapping("/search")
    public ObjectListResponse<Subcategory> getSubcategories(
            @RequestParam(required = false, name = "subcategoryId") String subcategoryId,
            @RequestParam(required = false, name = "categoryId") String categoryId,
            @RequestParam(required = false, name = "searchInput") String searchInput
    ) {

        Category category = null;
        if(categoryId != null){
            category = categoryService.getCategoryById(categoryId);
        }
        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withMatcher("name", contains().ignoreCase());
        var subcategoryFilter = Subcategory.builder()
                .id(subcategoryId)
                .name(searchInput)
                .category(category)
                .build();

        return new ObjectListResponse<>(200, subcategoryService.getAllSubcategoriesWithFilter(Example.of(subcategoryFilter, matcher)), null);
    }
}
