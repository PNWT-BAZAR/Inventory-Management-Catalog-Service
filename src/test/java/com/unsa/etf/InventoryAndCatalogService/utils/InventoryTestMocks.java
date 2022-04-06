package com.unsa.etf.InventoryAndCatalogService.utils;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;

public class InventoryTestMocks {
    public static Product getProductMock(String name, String description, Category category, Subcategory subcategory){
        return Product.builder()
                .name(name)
                .description(description)
                .quantity(20)
                .category(category)
                .subcategory(subcategory)
                .build();
    }

    public static Category getCategoryMock(String name){
        return new Category(name);
    }

    public static Subcategory getSubcategoryMock(String subcategoryName, Category category){
        return new Subcategory(subcategoryName, category);
    }
}
