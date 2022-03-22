package com.unsa.etf.InventoryAndCatalogService.services;

import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.model.ProductImages;
import com.unsa.etf.InventoryAndCatalogService.repositories.ProductImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImagesService {
    private final ProductImagesRepository productImagesRepository;

    @Autowired
    public ProductImagesService(ProductImagesRepository productImagesRepository) {
        this.productImagesRepository = productImagesRepository;
    }

    public List<ProductImages> getAllProductImages() {
        return productImagesRepository.findAll();
    }

    public ProductImages getProductImageById(String id) {
        return productImagesRepository.findById(id).get();
    }

    public void deleteProductImageById(String id) {
        productImagesRepository.deleteById(id);
    }

    public void createOrUpdateProductImage(ProductImages productImages) {
        productImagesRepository.save(productImages);
    }

    // TODO: 22.03.2022. get images
}
