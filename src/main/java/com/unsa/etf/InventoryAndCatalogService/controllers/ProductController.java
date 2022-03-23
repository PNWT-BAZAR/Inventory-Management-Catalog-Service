package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.services.ProductService;
import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final InventoryAndCatalogValidator inventoryAndCatalogValidator;

    @Autowired
    public ProductController(ProductService productService, InventoryAndCatalogValidator inventoryAndCatalogValidator) {
        this.productService = productService;
        this.inventoryAndCatalogValidator = inventoryAndCatalogValidator;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<?> createNewProduct(@RequestBody Product product) {
        if (inventoryAndCatalogValidator.isValid(product))
            return ResponseEntity.status(200).body(productService.createOrUpdateProduct(product));
        else
            return ResponseEntity.status(409).body(inventoryAndCatalogValidator.determineConstraintViolation(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        boolean deleted = productService.deleteProductById(id);
        if (deleted)
            return ResponseEntity.status(200).body("Object successfully deleted");
        return ResponseEntity.status(409).body("Object with id: " + id + " does not exist");
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        if (!inventoryAndCatalogValidator.isValid(product))
            return ResponseEntity.status(409).body(inventoryAndCatalogValidator.determineConstraintViolation(product));

        Product newProduct = productService.createOrUpdateProduct(product);
        return ResponseEntity.status(200).body("Successfully updated product: " + newProduct);
    }

}
