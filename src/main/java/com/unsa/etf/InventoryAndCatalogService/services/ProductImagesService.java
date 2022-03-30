package com.unsa.etf.InventoryAndCatalogService.services;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.model.ProductImages;
import com.unsa.etf.InventoryAndCatalogService.repositories.ProductImagesRepository;
import com.unsa.etf.InventoryAndCatalogService.utils.PaginatedObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if(productImagesRepository.existsById(id)) {
            return productImagesRepository.findById(id).get();
        }
        return null;
    }

    public boolean deleteProductImageById(String id) {
        if (productImagesRepository.existsById(id)) {
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


    //Sorting and Pagination
    public PaginatedObjectResponse<ProductImages> readAndSortProductImages (Pageable pageable){
        Page<ProductImages> productImages = productImagesRepository.findAll(pageable);
        return new PaginatedObjectResponse<>(productImages.getContent(), productImages.getTotalElements(), productImages.getTotalPages());
    }
}
