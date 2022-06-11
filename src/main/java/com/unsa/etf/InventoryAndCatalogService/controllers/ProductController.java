package com.unsa.etf.InventoryAndCatalogService.controllers;

import com.unsa.etf.InventoryAndCatalogService.insertObject.ProductReview;
import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.rabbitmq.RabbitMessageSender;
import com.unsa.etf.InventoryAndCatalogService.responses.BadRequestResponseBody;
import com.unsa.etf.InventoryAndCatalogService.responses.ObjectDeletionResponse;
import com.unsa.etf.InventoryAndCatalogService.responses.ObjectListResponse;
import com.unsa.etf.InventoryAndCatalogService.responses.ObjectResponse;
import com.unsa.etf.InventoryAndCatalogService.services.CategoryService;
import com.unsa.etf.InventoryAndCatalogService.services.ProductService;
import com.unsa.etf.InventoryAndCatalogService.services.SubcategoryService;
import com.unsa.etf.InventoryAndCatalogService.validators.InventoryAndCatalogValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;
    private final InventoryAndCatalogValidator inventoryAndCatalogValidator;
    private final RabbitMessageSender rabbitMessageSender;

//    @GetMapping("/rabbit")
//    public void testRabbitMq(){
//        rabbitTemplate.convertAndSend(InventoryAndCatalogServiceApplication.topicExchangeName, "foo.bar.#", Product.builder().name("kemo").build());
//    }


    @GetMapping
    public ObjectListResponse<Product> getAllProducts() {
        return new ObjectListResponse<>(200, productService.getAllProducts(), null);
    }

    @GetMapping("/{id}")
    public ObjectResponse<Product> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return new ObjectResponse<>(409, null, new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Product Does Not Exist!"));
        }
        return new ObjectResponse<>(200, product, null);
    }

    @PostMapping
    public ObjectResponse<Product> createNewProduct(@RequestBody Product product) {
        if (inventoryAndCatalogValidator.isValid(product)) {
            Product newProduct = productService.createOrUpdateProduct(product);
            rabbitMessageSender.notifyOrderServiceOfChange(newProduct, "add");
            return new ObjectResponse<>(200, product, null);
        }
        return new ObjectResponse<>(409, null, inventoryAndCatalogValidator.determineConstraintViolation(product));
    }

    @DeleteMapping("/{id}")
    public ObjectDeletionResponse deleteProduct(@PathVariable String id) {
        var productToBeDeleted = productService.getProductById(id);
        boolean deleted = productService.deleteProductById(id);
        if (deleted) {
            rabbitMessageSender.notifyOrderServiceOfChange(productToBeDeleted, "delete");
            return new ObjectDeletionResponse(200, "Product successfully deleted!", null);
        }
        return new ObjectDeletionResponse(409, "Error has occurred!", new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Product Does Not Exist!"));
    }

    @PutMapping
    public ObjectResponse<Product> updateProduct(@RequestBody Product product) {
        if (inventoryAndCatalogValidator.isValid(product)) {
            Product updatedProduct = productService.createOrUpdateProduct(product);
            rabbitMessageSender.notifyOrderServiceOfChange(updatedProduct, "update");
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
            rabbitMessageSender.notifyOrderServiceOfChange(updatedProduct, "update");
            return new ObjectResponse<>(200, updatedProduct, null);
        }
        return new ObjectResponse<>(409, null, new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Product Does Not Exist!"));
    }


    @GetMapping("/search")
    public ObjectListResponse<Product> getProductsWithFilter(
            @RequestParam(required = false, name = "subcategoryId") String subcategoryId,
            @RequestParam(required = false, name = "categoryId") String categoryId,
            @RequestParam(required = false, name = "searchInput") String searchInput
    ) {

        Category category = null;
        Subcategory subcategory = null;
        if (categoryId != null) {
            category = categoryService.getCategoryById(categoryId);
        }
        if (subcategoryId != null) {
            subcategory = subcategoryService.getSubcategoryById(subcategoryId);
        }

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withMatcher("name", contains().ignoreCase());
        var productFilter = Product.builder()
                .name(searchInput)
                .category(category)
                .subcategory(subcategory)
                .build();

        System.out.println(productFilter);
        var lista = productService.getAllProductsWithFilter(Example.of(productFilter, matcher));
        System.out.println(lista);
        return new ObjectListResponse<>(200, productService.getAllProductsWithFilter(Example.of(productFilter, matcher)), null);
    }

}
