package com.unsa.etf.InventoryAndCatalogService.services;

import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.repositories.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        var subcategory = subcategoryRepository.findById(id);
        if(subcategory.isPresent())
            return subcategoryRepository.findById(id).get();
        return null;
    }

    public boolean deleteSubcategoryById(String id) {
        if(getSubcategoryById(id) != null){
            subcategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Subcategory createOrUpdateSubcategory(Subcategory newSubcategory) {
        subcategoryRepository.save(newSubcategory);
        return newSubcategory;
    }
}
