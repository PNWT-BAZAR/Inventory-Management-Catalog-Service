package com.unsa.etf.InventoryAndCatalogService.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderServiceProduct {
    private String id;

    private String name;

    private String description;

    private int quantity;

    private float price;

    private int reviewSum;

    private int totalReviews;

    private String categoryId;

    private String subcategoryId;

    public OrderServiceProduct(String name,
                                      String description,
                                      int quantity,
                                      float price,
                                      int reviewSum,
                                      int totalReviews,
                                      String categoryId,
                                      String subcategoryId) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.reviewSum = reviewSum;
        this.totalReviews = totalReviews;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
    }
}
