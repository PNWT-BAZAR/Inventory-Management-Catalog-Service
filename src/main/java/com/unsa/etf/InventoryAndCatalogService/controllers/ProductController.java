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

    @GetMapping("/get")
    public List<Product> getAllProducts (){
        return productService.getAllProducts();
    }

    @GetMapping("/get/{id}")
    public Product getAllProducts (@PathVariable String id){
        return productService.getProductById(id);
    }

    @PostMapping("/create")
    public void createNewProduct (@RequestBody Product product){
        productService.createProduct(product);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct (@PathVariable String id){
        productService.deleteProductById(id);
    }

    @PutMapping("/update")
    public void updateProduct (@RequestBody Product product){
        productService.updateProduct(product);
    }

}
