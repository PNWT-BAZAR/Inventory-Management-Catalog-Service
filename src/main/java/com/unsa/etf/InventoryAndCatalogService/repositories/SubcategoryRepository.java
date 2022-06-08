package com.unsa.etf.InventoryAndCatalogService.repositories;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, String> {
    @Query("SELECT s FROM Subcategory s WHERE s.name=?1")
    List<Subcategory> findSubcategoriesByName(String name);

    List<Subcategory> findSubcategoriesByCategory(Category category);
}
