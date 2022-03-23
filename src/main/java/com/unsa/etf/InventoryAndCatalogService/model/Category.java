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
public class Category {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    private String name;

    public Category(String name) {
        this.name = name;
    }
}
