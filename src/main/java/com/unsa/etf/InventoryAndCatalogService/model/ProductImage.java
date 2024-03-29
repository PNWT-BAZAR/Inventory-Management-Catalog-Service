package com.unsa.etf.InventoryAndCatalogService.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    private String url;

//    private byte[] image;

    //private BinaryData image;
    @ManyToOne
    @JoinColumn(name = "productId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    public ProductImage(String url, Product product) {
        this.url = url;
        this.product = product;
    }
}
