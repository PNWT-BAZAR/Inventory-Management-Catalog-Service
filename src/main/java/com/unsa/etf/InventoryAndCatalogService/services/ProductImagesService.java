package com.unsa.etf.InventoryAndCatalogService.services;

import com.unsa.etf.InventoryAndCatalogService.model.ProductImage;
import com.unsa.etf.InventoryAndCatalogService.repositories.ProductImagesRepository;
import com.unsa.etf.InventoryAndCatalogService.responses.PaginatedObjectResponse;
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

    public List<ProductImage> getAllProductImages() {
        return productImagesRepository.findAll();
    }

    public ProductImage getProductImageById(String id) {
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

    public ProductImage createOrUpdateProductImage(ProductImage productImage) {
        productImagesRepository.save(productImage);
        return productImage;
    }

    // TODO: 22.03.2022. get images


    //Sorting and Pagination
    public PaginatedObjectResponse<ProductImage> readAndSortProductImages (Pageable pageable){
        Page<ProductImage> productImages = productImagesRepository.findAll(pageable);
        return new PaginatedObjectResponse<>(200, productImages.getContent(), productImages.getTotalElements(), productImages.getTotalPages(), null);
    }

    public List<ProductImage> getProductImageByProductId(String id) {
        return productImagesRepository.getImagesByProductId(id);
    }
}
