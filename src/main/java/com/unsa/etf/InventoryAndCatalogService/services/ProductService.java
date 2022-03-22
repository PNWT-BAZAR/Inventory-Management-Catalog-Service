package com.unsa.etf.InventoryAndCatalogService.services;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.repositories.ProductImagesRepository;
import com.unsa.etf.InventoryAndCatalogService.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService (ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Integer id){
        return productRepository.findById(id).get();
    }

    public void deleteProductById(Integer id){
        productRepository.deleteById(id);
    }

    public void updateProduct(Integer id, Product newProduct){
        var product = getProductById(id);
        product = newProduct;
    }

    public void createProduct(String name, String description, int quantity, float price, Category category, Subcategory subcategory, int reviewSum, int totalReviews){
        var createdProduct = new Product(name, description, quantity, price, category, subcategory, reviewSum, totalReviews);
        productRepository.save(createdProduct);
    }



    // TODO: 22.03.2022. get images
}
