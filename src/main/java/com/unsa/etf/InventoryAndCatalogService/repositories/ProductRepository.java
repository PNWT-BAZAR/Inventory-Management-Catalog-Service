package com.unsa.etf.InventoryAndCatalogService.repositories;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{
    @Query("SELECT p FROM Product p WHERE p.name=?1")
    List<Product> findProductsByName(String name);

    @Query("SELECT p FROM Product p WHERE p.category.name = ?1 AND p.subcategory.name = ?2")
    Page<Product> findProductByCategoryAndSubcategory(String category, String subcategory, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.name = ?1")
    Page<Product> findProductByCategory(String category, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.subcategory.name = ?2")
    Page<Product> findProductBySubcategory(String subcategory, Pageable pageable);

    List<Product> findByNameContaining (@Param(value = "name") String name);
}
