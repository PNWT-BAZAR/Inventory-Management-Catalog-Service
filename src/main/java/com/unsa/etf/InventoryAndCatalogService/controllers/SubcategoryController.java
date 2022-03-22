package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.services.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subcategories")
public class SubcategoryController {
    private final SubcategoryService subcategoryService;

    @Autowired
    public SubcategoryController(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping("/get")
    public List<Subcategory> getAllSubcategories() {
        return subcategoryService.getAllSubcategories();
    }

    @GetMapping("/get/{id}")
    public Subcategory getSubcategoryById(@PathVariable String id) {
        return subcategoryService.getSubcategoryById(id);
    }

    @PostMapping("/create")
    public void createNewSubcategory(@RequestBody Subcategory subcategory) {
        subcategoryService.createOrUpdateSubcategory(subcategory);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSubcategory(@PathVariable String id) {
        subcategoryService.deleteSubcategoryById(id);
    }

    @PutMapping("/update")
    public void updateSubcategory(@RequestBody Subcategory subcategory) {
        subcategoryService.createOrUpdateSubcategory(subcategory);
    }

}
