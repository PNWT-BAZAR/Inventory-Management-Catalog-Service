package com.unsa.etf.InventoryAndCatalogService.utils;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;

public class InventoryTestMocks {
    public static Product getProductMock(String name, String description, Category category){
        return Product.builder()
                .name(name)
                .description(description)
                .quantity(20)
                .category(category)
                .build();
    }
}
