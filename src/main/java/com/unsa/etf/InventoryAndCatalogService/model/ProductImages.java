package com.unsa.etf.InventoryAndCatalogService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table
public class ProductImages {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    //private BinaryData image;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public ProductImages(Product product) {
        this.product = product;
    }
}
