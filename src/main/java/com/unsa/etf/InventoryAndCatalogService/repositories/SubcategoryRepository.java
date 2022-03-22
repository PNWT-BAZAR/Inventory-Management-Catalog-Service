package com.unsa.etf.InventoryAndCatalogService.repositories;

import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

}
