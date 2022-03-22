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
public class Subcategory {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    public Subcategory(String name, Category category) {
        this.name = name;
        this.category = category;
    }
}
