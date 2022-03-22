package com.unsa.etf.InventoryAndCatalogService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table
public class Product {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String description;
    private int quantity;
    private float price;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategoryId")
    private Subcategory subcategory;

    private int reviewSum;
    private int totalReviews;

    public Product(String name, String description, int quantity, float price, Category category, Subcategory subcategory, int reviewSum, int totalReviews) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.subcategory = subcategory;
        this.reviewSum = reviewSum;
        this.totalReviews = totalReviews;
    }
}
