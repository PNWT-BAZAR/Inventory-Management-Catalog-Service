package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.model.ProductImage;
import com.unsa.etf.InventoryAndCatalogService.responses.*;
import com.unsa.etf.InventoryAndCatalogService.services.ProductImagesService;
import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productImages")
public class ProductImageController {
    private final ProductImagesService productImagesService;
    private final InventoryAndCatalogValidator inventoryAndCatalogValidator;

//    @Autowired
//    public ProductImageController(ProductImagesService productImagesService, InventoryAndCatalogValidator inventoryAndCatalogValidator) {
//        this.productImagesService = productImagesService;
//        this.inventoryAndCatalogValidator = inventoryAndCatalogValidator;
//    }

    @GetMapping
    public ObjectListResponse<ProductImage> getAllProductImages() {
        return new ObjectListResponse<>(200, productImagesService.getAllProductImages(), null);
    }

    @GetMapping("/{id}")
    public ObjectResponse<ProductImage> getProductImageById(@PathVariable String id) {
        ProductImage productImage = productImagesService.getProductImageById(id);
        if (productImage == null) {
            return new ObjectResponse<>(409, null, new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Product image Does Not Exist!"));
        }
        return new ObjectResponse<>(200, productImage, null);
    }

    @GetMapping("/product/{id}")
    public ObjectListResponse<ProductImage> getProductImageByProductId(@PathVariable String id) {
        List<ProductImage> productImages = productImagesService.getProductImageByProductId(id);
//        if (productImage == null) {
//            return new ObjectResponse<>(409, null, new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Product image Does Not Exist!"));
//        }
        return new ObjectListResponse<>(200, productImages, null);
    }

    @PostMapping
    public ObjectResponse<ProductImage> createNewProductImage(@RequestBody ProductImage productImage) {
        if (inventoryAndCatalogValidator.isValid(productImage)) {
            ProductImage newProductImage = productImagesService.createOrUpdateProductImage(productImage);
            return new ObjectResponse<>(200, productImage, null);
        }
        return new ObjectResponse<>(409, null, inventoryAndCatalogValidator.determineConstraintViolation(productImage));
    }

    @DeleteMapping("/{id}")
    public ObjectDeletionResponse deleteProductImage(@PathVariable String id) {
        boolean deleted = productImagesService.deleteProductImageById(id);
        if (deleted)
            return new ObjectDeletionResponse(200, "Product image successfully deleted", null);
        return new ObjectDeletionResponse(409, "An error has occurred!", new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Product image Does Not Exist!"));
    }

    @PutMapping
    public ObjectResponse<ProductImage> updateProductImage(@RequestBody ProductImage productImage) {
        if (inventoryAndCatalogValidator.isValid(productImage)) {
            ProductImage updatedProductImage = productImagesService.createOrUpdateProductImage(productImage);
            return new ObjectResponse<>(200, updatedProductImage, null);
        }
        return new ObjectResponse<>(409, null, inventoryAndCatalogValidator.determineConstraintViolation(productImage));
    }

    //Sorting and Pagination
    @GetMapping("/search")
    public PaginatedObjectResponse<ProductImage> readProductImages (Pageable pageable){
        try{
            return productImagesService.readAndSortProductImages(pageable);
        }catch (PropertyReferenceException e){
            return PaginatedObjectResponse.<ProductImage>builder().statusCode(409).error(new BadRequestResponseBody (BadRequestResponseBody.ErrorCode.NOT_FOUND, e.getMessage())).build();
        }
    }

}
