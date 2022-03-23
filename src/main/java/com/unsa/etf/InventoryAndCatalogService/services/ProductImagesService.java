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
        var productImage = productImagesRepository.findById(id);
        if(productImage.isPresent())
            return productImagesRepository.findById(id).get();
        return null;
    }

    public boolean deleteProductImageById(String id) {
        if(getProductImageById(id) != null){
            productImagesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProductImages createOrUpdateProductImage(ProductImages productImages) {
        productImagesRepository.save(productImages);
        return productImages;
    }

    // TODO: 22.03.2022. get images
}
