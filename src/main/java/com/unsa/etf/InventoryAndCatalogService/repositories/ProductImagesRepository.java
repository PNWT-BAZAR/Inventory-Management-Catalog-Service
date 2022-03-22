package com.unsa.etf.InventoryAndCatalogService.repositories;

import com.unsa.etf.InventoryAndCatalogService.model.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImages, Integer> {

}
