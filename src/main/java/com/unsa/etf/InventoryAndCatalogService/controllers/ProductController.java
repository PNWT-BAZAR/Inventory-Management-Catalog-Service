package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.services.ProductService;
import com.unsa.etf.InventoryAndCatalogService.responses.BadRequestResponseBody;
import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
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
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Product Does Not Exist!"));
        }
        return ResponseEntity.status(200).body(product);
    }

    @PostMapping
    public ResponseEntity<?> createNewProduct(@RequestBody Product product) {
        if (inventoryAndCatalogValidator.isValid(product)) {
            Product newProduct = productService.createOrUpdateProduct(product);
            return ResponseEntity.status(200).body(product);
        }
        return ResponseEntity.status(409).body(inventoryAndCatalogValidator.determineConstraintViolation(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        boolean deleted = productService.deleteProductById(id);
        if (deleted)
            return ResponseEntity.status(200).body("Product successfully deleted!");
        return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Product Does Not Exist!"));
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        if (inventoryAndCatalogValidator.isValid(product)) {
            Product updatedProduct = productService.createOrUpdateProduct(product);
            return ResponseEntity.status(200).body(updatedProduct);
        }
        return ResponseEntity.status(409).body(inventoryAndCatalogValidator.determineConstraintViolation(product));
    }


    //Sorting and Pagination
    @GetMapping("/search")
    public ResponseEntity<?> readProducts (Pageable pageable){
        try{
            return ResponseEntity.status(200).body(productService.readAndSortProducts(pageable));
        }catch (PropertyReferenceException e){
            return ResponseEntity.status(409).body(new BadRequestResponseBody (BadRequestResponseBody.ErrorCode.NOT_FOUND, e.getMessage()));
        }
    }

    //Filtering
    @GetMapping("/filter")
    public ResponseEntity readProductsWithFilter (@RequestParam(value = "category", required = false) String category, @RequestParam(value = "subcategory", required = false) String subcategory, Pageable pageable) {
        if(category != null && subcategory != null){
            return ResponseEntity.status(200).body(productService.filterProductsByCategoryAndSubcategory(category, subcategory, pageable));
        }else if(category != null){
            return ResponseEntity.status(200).body(productService.filterProductsByCategory(category, pageable));
        }else if(subcategory != null){
            return ResponseEntity.status(200).body(productService.filterProductsBySubcategory(subcategory, pageable));
        }
        System.out.println("test");
        return ResponseEntity.status(409).body(new BadRequestResponseBody (BadRequestResponseBody.ErrorCode.NOT_FOUND, "test"));
    }

}
