package com.unsa.etf.InventoryAndCatalogService.services;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.repositories.CategoryRepository;
import com.unsa.etf.InventoryAndCatalogService.responses.PaginatedObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if(categoryRepository.existsById(id)) {
            return categoryRepository.findById(id).get();
        }
        return null;
    }

    public List<Category> getCategoryByName (String name){
        return categoryRepository.findCategoriesByName(name);
    }

    public boolean deleteCategoryById(String id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

//    public Category createNewCategory(Category category){
////
////    }

    public Category createOrUpdateCategory(Category newCategory) {
        categoryRepository.save(newCategory);
        return newCategory;
    }

    public List<Category> searchCategoriesByName (String searchInput){
        return categoryRepository.findByNameContaining(searchInput);
    }

    //Sorting and Pagination
    public PaginatedObjectResponse<Category> readAndSortCategories (Pageable pageable){

        Page<Category> categories = categoryRepository.findAll(pageable);
        return new PaginatedObjectResponse<>(200, categories.getContent(), categories.getTotalElements(), categories.getTotalPages(), null);
    }
}
