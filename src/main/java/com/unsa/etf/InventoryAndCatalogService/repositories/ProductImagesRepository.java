package com.unsa.etf.InventoryAndCatalogService.repositories;

import com.unsa.etf.InventoryAndCatalogService.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImage, String> {
    @Query("SELECT pi FROM ProductImage pi WHERE pi.product.id=?1")
    List<ProductImage> getImagesByProductId(String id);
}
