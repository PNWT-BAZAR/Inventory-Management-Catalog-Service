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
public class ProductImages {
    @Id
    @GeneratedValue
    private int id;

    //private BinaryData image;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public ProductImages(Product product) {
        this.product = product;
    }
}
