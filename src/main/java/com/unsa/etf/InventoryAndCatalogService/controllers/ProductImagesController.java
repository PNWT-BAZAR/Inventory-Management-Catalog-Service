package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.ProductImages;
import com.unsa.etf.InventoryAndCatalogService.responses.*;
import com.unsa.etf.InventoryAndCatalogService.services.ProductImagesService;
import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
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
    public ObjectListResponse<ProductImages> getAllProductImages() {
        return new ObjectListResponse<>(200, productImagesService.getAllProductImages(), null);
    }

    @GetMapping("/{id}")
    public ObjectResponse<ProductImages> getProductImageById(@PathVariable String id) {
        ProductImages productImages = productImagesService.getProductImageById(id);
        if (productImages == null) {
            return new ObjectResponse<>(409, null, new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Product image Does Not Exist!"));
        }
        return new ObjectResponse<>(200, productImages, null);
    }

    @PostMapping
    public ObjectResponse<ProductImages> createNewProductImage(@RequestBody ProductImages productImages) {
        if (inventoryAndCatalogValidator.isValid(productImages)) {
            ProductImages newProductImage = productImagesService.createOrUpdateProductImage(productImages);
            return new ObjectResponse<>(200, productImages, null);
        }
        return new ObjectResponse<>(409, null, inventoryAndCatalogValidator.determineConstraintViolation(productImages));
    }

    @DeleteMapping("/{id}")
    public ObjectDeletionResponse deleteProductImage(@PathVariable String id) {
        boolean deleted = productImagesService.deleteProductImageById(id);
        if (deleted)
            return new ObjectDeletionResponse(200, "Product image successfullz deleted", null);
        return new ObjectDeletionResponse(409, "An error has occurred!", new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Product image Does Not Exist!"));
    }

    @PutMapping
    public ObjectResponse<ProductImages> updateProductImage(@RequestBody ProductImages productImages) {
        if (inventoryAndCatalogValidator.isValid(productImages)) {
            ProductImages updatedProductImage = productImagesService.createOrUpdateProductImage(productImages);
            return new ObjectResponse<>(200, updatedProductImage, null);
        }
        return new ObjectResponse<>(409, null, inventoryAndCatalogValidator.determineConstraintViolation(productImages));
    }

    //Sorting and Pagination
    @GetMapping("/search")
    public PaginatedObjectResponse<ProductImages> readProductImages (Pageable pageable){
        try{
            return productImagesService.readAndSortProductImages(pageable);
        }catch (PropertyReferenceException e){
            return PaginatedObjectResponse.<ProductImages>builder().statusCode(409).error(new BadRequestResponseBody (BadRequestResponseBody.ErrorCode.NOT_FOUND, e.getMessage())).build();
        }
    }

}
