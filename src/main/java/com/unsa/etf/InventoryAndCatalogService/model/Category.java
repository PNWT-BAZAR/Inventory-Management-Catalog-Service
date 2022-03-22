package com.unsa.etf.InventoryAndCatalogService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table
public class Category {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    public Category(String name) {
        this.name = name;
    }
}
