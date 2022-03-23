package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.model.ProductImages;
import com.unsa.etf.InventoryAndCatalogService.services.ProductImagesService;
import com.unsa.etf.InventoryAndCatalogService.services.ProductService;
import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productImages")
public class ProductImagesController {
    private final ProductImagesService productImagesService;
    private final InventoryAndCatalogValidator inventoryAndCatalogValidator;

    @Autowired
    public ProductImagesController(ProductImagesService productImagesService, InventoryAndCatalogValidator inventoryAndCatalogValidator) {
        this.productImagesService = productImagesService;
        this.inventoryAndCatalogValidator = inventoryAndCatalogValidator;
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
    public ResponseEntity<?> createNewProductImage(@RequestBody ProductImages productImages) {
        return ResponseEntity.status(200).body(productImagesService.createOrUpdateProductImage(productImages));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductImage(@PathVariable String id) {
        boolean deleted = productImagesService.deleteProductImageById(id);
        if (deleted)
            return ResponseEntity.status(200).body("Object successfully deleted");
        return ResponseEntity.status(409).body("Object with id: " + id + " does not exist");
    }

    @PutMapping
    public ResponseEntity<?> updateProductImage(@RequestBody ProductImages productImages) {
        if (!inventoryAndCatalogValidator.isValid(productImages))
            return ResponseEntity.status(409).body(inventoryAndCatalogValidator.determineConstraintViolation(productImages));

        ProductImages newProductImages = productImagesService.createOrUpdateProductImage(productImages);
        return ResponseEntity.status(200).body("Successfully updated product: " + newProductImages);
    }

}
