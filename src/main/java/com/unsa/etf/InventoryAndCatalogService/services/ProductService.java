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
        var product = productRepository.findById(id);
        if(product.isPresent())
            return productRepository.findById(id).get();
        return null;
    }

    public boolean deleteProductById(String id) {
        if(getProductById(id) != null){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

//    public void updateProduct(Product newProduct){
//        productRepository.save(newProduct);
//    }
//
//    public void createProduct(Product product){
//        productRepository.save(product);
//    }

    public Product createOrUpdateProduct(Product newProduct) {
        productRepository.save(newProduct);
        return newProduct;
    }
}
