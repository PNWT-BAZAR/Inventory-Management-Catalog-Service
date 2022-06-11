package com.unsa.etf.InventoryAndCatalogService.services;

import com.unsa.etf.InventoryAndCatalogService.InventoryAndCatalogServiceApplication;
import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.repositories.ProductRepository;
import com.unsa.etf.InventoryAndCatalogService.responses.PaginatedObjectResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if(productRepository.existsById(id)) {
            return productRepository.findById(id).get();
        }
        return null;
    }

    public boolean deleteProductById(String id) {
        if (productRepository.existsById(id)) {
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

    //Sorting and Pagination
    public PaginatedObjectResponse<Product> readAndSortProducts (Pageable pageable){
        Page<Product> products = productRepository.findAll(pageable);
        return new PaginatedObjectResponse<>(200, products.getContent(), products.getTotalElements(), products.getTotalPages(), null);
    }

    public List<Product> searchProductsByName (String searchInput){
        return productRepository.findByNameContaining(searchInput);
    }

    //Filtering
    public List<Product> getAllProductsWithFilter (Example<Product> filter){
        return productRepository.findAll(filter);
    }
}
