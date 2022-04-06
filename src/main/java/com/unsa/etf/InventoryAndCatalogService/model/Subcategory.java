package com.unsa.etf.InventoryAndCatalogService.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table
@NoArgsConstructor
public class Subcategory {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    public Subcategory(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        Subcategory otherSubcat = (Subcategory) other;
        return this.getName().equals(otherSubcat.getName()) && this.getCategory().equals(otherSubcat.getCategory());
    }
}
