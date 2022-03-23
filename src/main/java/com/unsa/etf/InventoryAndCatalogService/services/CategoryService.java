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
        return categoryRepository.findById(id).get();
    }

    public void deleteCategoryById(String id) {
        categoryRepository.deleteById(id);
    }

    public void createOrUpdateCategory(Category newCategory) {
        categoryRepository.save(newCategory);
    }
}
