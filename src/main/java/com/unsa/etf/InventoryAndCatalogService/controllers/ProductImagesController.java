package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.ProductImages;
import com.unsa.etf.InventoryAndCatalogService.services.ProductImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productImages")
public class ProductImagesController {
    private final ProductImagesService productImagesService;

    @Autowired
    public ProductImagesController(ProductImagesService productImagesService) {
        this.productImagesService = productImagesService;
    }

    @GetMapping
    public List<ProductImages> getAllProductImages() {
        return productImagesService.getAllProductImages();
    }

    @GetMapping("/{id}")
    public ProductImages getProductImageById(@PathVariable String id) {
        return productImagesService.getProductImageById(id);
    }

    @PostMapping
    public void createNewProductImage(@RequestBody ProductImages productImages) {
        productImagesService.createOrUpdateProductImage(productImages);
    }

    @DeleteMapping("/{id}")
    public void deleteProductImage(@PathVariable String id) {
        productImagesService.deleteProductImageById(id);
    }

    @PutMapping
    public void updateProductImage(@RequestBody ProductImages productImages) {
        productImagesService.createOrUpdateProductImage(productImages);
    }

}
