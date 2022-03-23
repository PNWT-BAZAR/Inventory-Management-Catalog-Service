package com.unsa.etf.InventoryAndCatalogService.services;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(String id) {
        var category = categoryRepository.findById(id);
        if(category.isPresent())
            return categoryRepository.findById(id).get();
        return null;
    }

    public boolean deleteCategoryById(String id) {
        if(getCategoryById(id) != null){
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Category createOrUpdateCategory(Category newCategory) {
        categoryRepository.save(newCategory);
        return newCategory;
    }
}
