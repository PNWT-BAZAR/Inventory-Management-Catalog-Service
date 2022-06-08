package com.unsa.etf.InventoryAndCatalogService.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table
@NoArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    private String name;

    private byte[] image;

    //private BinaryData image;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public ProductImage(String name, byte[] image, Product product) {
        this.name = name;
        this.image = image;
        this.product = product;
    }
}
