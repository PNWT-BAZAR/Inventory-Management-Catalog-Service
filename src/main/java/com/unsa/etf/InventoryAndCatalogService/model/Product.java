package com.unsa.etf.InventoryAndCatalogService.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

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

    @Size(min = 1, max = 250)
    @NotBlank
    private String name;

    @Size(min = 1, max = 1000)
    @NotBlank
    private String description;

    @Min(0)
    private Integer quantity;

    @Min(0)
    private Float price;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategoryId")
    private Subcategory subcategory;

    @Min(0)
    private Integer reviewSum;

    @Min(0)
    private Integer totalReviews;

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

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        Product otherProd = (Product)other;
        return this.getName().equals(otherProd.getName());
    }
}
