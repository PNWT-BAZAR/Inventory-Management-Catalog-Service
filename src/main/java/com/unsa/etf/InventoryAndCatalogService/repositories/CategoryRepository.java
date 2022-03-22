package com.unsa.etf.InventoryAndCatalogService.repositories;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
