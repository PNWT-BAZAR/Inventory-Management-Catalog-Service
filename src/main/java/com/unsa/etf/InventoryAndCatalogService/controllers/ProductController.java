package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController (ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts (){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById (@PathVariable String id){
        return productService.getProductById(id);
    }

    @PostMapping
    public void createNewProduct (@RequestBody Product product){
        productService.createOrUpdateProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct (@PathVariable String id){
        productService.deleteProductById(id);
    }

    @PutMapping
    public void updateProduct (@RequestBody Product product){
        productService.createOrUpdateProduct(product);
    }

}
