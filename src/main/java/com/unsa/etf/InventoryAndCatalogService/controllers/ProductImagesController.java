package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.model.ProductImages;
import com.unsa.etf.InventoryAndCatalogService.services.ProductImagesService;
import com.unsa.etf.InventoryAndCatalogService.services.ProductService;
import com.unsa.etf.InventoryAndCatalogService.validators.BadRequestResponseBody;
import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<?> getProductImageById(@PathVariable String id) {
        ProductImages productImages = productImagesService.getProductImageById(id);
        if (productImages == null) {
            return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Product image Does Not Exist!"));
        }
        return ResponseEntity.status(200).body(productImages);
    }

    @PostMapping
    public ResponseEntity<?> createNewProductImage(@RequestBody ProductImages productImages) {
        if (inventoryAndCatalogValidator.isValid(productImages)) {
            ProductImages newProductImage = productImagesService.createOrUpdateProductImage(productImages);
            return ResponseEntity.status(200).body(productImages);
        }
        return ResponseEntity.status(409).body(inventoryAndCatalogValidator.determineConstraintViolation(productImages));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductImage(@PathVariable String id) {
        boolean deleted = productImagesService.deleteProductImageById(id);
        if (deleted)
            return ResponseEntity.status(200).body("Product image successfully deleted");
        return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Product image Does Not Exist!"));
    }

    @PutMapping
    public ResponseEntity<?> updateProductImage(@RequestBody ProductImages productImages) {
        if (inventoryAndCatalogValidator.isValid(productImages)) {
            ProductImages updatedProductImage = productImagesService.createOrUpdateProductImage(productImages);
            return ResponseEntity.status(200).body(updatedProductImage);
        }
        return ResponseEntity.status(409).body(inventoryAndCatalogValidator.determineConstraintViolation(productImages));
    }

    //Sorting and Pagination
    @GetMapping("/search")
    public ResponseEntity<?> readProductImages (Pageable pageable){
        var x = pageable.getSort();
        for (var s : pageable.getSort()){
            System.out.println(s.getProperty());
        }
        // TODO: 30.03.2022. error handling kad sortiranje po nepostojecem
        return ResponseEntity.status(200).body(productImagesService.readAndSortProductImages(pageable));
    }

}
