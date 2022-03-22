package com.unsa.etf.InventoryAndCatalogService.services;

import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).get();
    }

    public void deleteProductById(String id) {
        productRepository.deleteById(id);
    }

//    public void updateProduct(Product newProduct){
//        productRepository.save(newProduct);
//    }
//
//    public void createProduct(Product product){
//        productRepository.save(product);
//    }

    public void createOrUpdateProduct(Product newProduct) {
        productRepository.save(newProduct);
    }
}
