package com.unsa.etf.InventoryAndCatalogService.services;

import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.repositories.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;

    @Autowired
    public SubcategoryService(SubcategoryRepository subcategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
    }

    public List<Subcategory> getAllSubcategories() {
        return subcategoryRepository.findAll();
    }

    public Subcategory getSubcategoryById(String id) {
        return subcategoryRepository.findById(id).get();
    }

    public void deleteSubcategoryById(String id) {
        subcategoryRepository.deleteById(id);
    }

    public void createOrUpdateSubcategory(Subcategory newSubcategory) {
        subcategoryRepository.save(newSubcategory);
    }
}
