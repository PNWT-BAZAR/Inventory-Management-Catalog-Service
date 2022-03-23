package com.unsa.etf.InventoryAndCatalogService.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Min(0)
    private int quantity;

    @Min(0)
    private float price;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategoryId")
    private Subcategory subcategory;

    @Min(0)
    private int reviewSum;

    @Min(0)
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
