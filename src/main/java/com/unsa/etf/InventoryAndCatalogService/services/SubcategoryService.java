package com.unsa.etf.InventoryAndCatalogService.services;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.repositories.SubcategoryRepository;
import com.unsa.etf.InventoryAndCatalogService.utils.PaginatedObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if(subcategoryRepository.existsById(id)) {
            return subcategoryRepository.findById(id).get();
        }
        return null;
    }

    public boolean deleteSubcategoryById(String id) {
        if (subcategoryRepository.existsById(id)) {
            subcategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Subcategory createOrUpdateSubcategory(Subcategory newSubcategory) {
        subcategoryRepository.save(newSubcategory);
        return newSubcategory;
    }

    //Sorting and Pagination
    public PaginatedObjectResponse<Subcategory> readAndSortSubcategories (Pageable pageable){
        Page<Subcategory> subcategories = subcategoryRepository.findAll(pageable);
        return new PaginatedObjectResponse<>(subcategories.getContent(), subcategories.getTotalElements(), subcategories.getTotalPages());
    }
}
