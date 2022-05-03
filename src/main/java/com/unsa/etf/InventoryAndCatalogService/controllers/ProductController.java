package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.insertObject.ProductReview;
import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.responses.*;
import com.unsa.etf.InventoryAndCatalogService.services.ProductService;
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
    public ObjectListResponse<Product> getAllProducts() {
        return new ObjectListResponse<>(200, productService.getAllProducts(), null);
    }

    @GetMapping("/{id}")
    public ObjectResponse<Product> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return new ObjectResponse<>(409, null, new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Product Does Not Exist!" ));
        }
        return new ObjectResponse<>(200, product, null);
    }

    @PostMapping
    public ObjectResponse<Product> createNewProduct(@RequestBody Product product) {
        if (inventoryAndCatalogValidator.isValid(product)) {
            Product newProduct = productService.createOrUpdateProduct(product);
            return new ObjectResponse<>(200, product, null);
        }
        return new ObjectResponse<>(409, null, inventoryAndCatalogValidator.determineConstraintViolation(product));
    }

    @DeleteMapping("/{id}")
    public ObjectDeletionResponse deleteProduct(@PathVariable String id) {
        boolean deleted = productService.deleteProductById(id);
        if (deleted)
            return new ObjectDeletionResponse(200, "Product successfully deleted!", null);
        return new ObjectDeletionResponse(409, "Error has occurred!", new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Product Does Not Exist!"));
    }

    @PutMapping
    public ObjectResponse<Product> updateProduct(@RequestBody Product product) {
        if (inventoryAndCatalogValidator.isValid(product)) {
            Product updatedProduct = productService.createOrUpdateProduct(product);
            return new ObjectResponse<>(200, updatedProduct, null);
        }
        return new ObjectResponse<>(409, null, inventoryAndCatalogValidator.determineConstraintViolation(product));
    }

    @PutMapping("/reviewProduct/{id}")
    public ObjectResponse<Product> reviewProductById(@PathVariable String id, @RequestBody ProductReview productReview) {
        Product product = productService.getProductById(id);
        if (product != null) {
            product.setReviewSum(product.getReviewSum() + productReview.getReviewValue());
            product.setTotalReviews(product.getTotalReviews() + 1);

            Product updatedProduct = productService.createOrUpdateProduct(product);
            return new ObjectResponse<>(200, updatedProduct, null);
        }
        return new ObjectResponse<>(409, null, new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Product Does Not Exist!"));
    }

    //Sorting and Pagination
    @GetMapping("/search")
    public PaginatedObjectResponse<Product> readProducts (Pageable pageable){
        try{
            return productService.readAndSortProducts(pageable);
        }catch (PropertyReferenceException e){
            return PaginatedObjectResponse.<Product>builder().statusCode(409).error(new BadRequestResponseBody (BadRequestResponseBody.ErrorCode.NOT_FOUND, e.getMessage())).build();
        }
    }

    //Filtering
    @GetMapping("/filter")
    public PaginatedObjectResponse<Product> readProductsWithFilter (@RequestParam(value = "category", required = false) String category, @RequestParam(value = "subcategory", required = false) String subcategory, Pageable pageable) {
        if(category != null && subcategory != null){
            return productService.filterProductsByCategoryAndSubcategory(category, subcategory, pageable);
        }else if(category != null){
            return productService.filterProductsByCategory(category, pageable);
        }else if(subcategory != null){
            return productService.filterProductsBySubcategory(subcategory, pageable);
        }
        return PaginatedObjectResponse.<Product>builder().statusCode(409).error(new BadRequestResponseBody (BadRequestResponseBody.ErrorCode.NOT_FOUND, "An error has occurred!")).build();
    }

}
