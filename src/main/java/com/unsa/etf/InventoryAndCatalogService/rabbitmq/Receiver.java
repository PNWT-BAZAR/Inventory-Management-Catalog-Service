package com.unsa.etf.InventoryAndCatalogService.rabbitmq;

import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.services.CategoryService;
import com.unsa.etf.InventoryAndCatalogService.services.ProductService;
import com.unsa.etf.InventoryAndCatalogService.services.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Receiver {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;

    @RabbitListener(queues = "orders-to-inventory")
    public void receiveMessage(ProductRabbitReceiverModel message) {
        var receivedProduct = message.getProduct();
        var failedOperation = message.getOperation();
        System.out.println(receivedProduct);
        System.out.println(receivedProduct);
        System.out.println(failedOperation);

        switch (failedOperation){
            case "add":
                onAddFailed(receivedProduct);
                break;
            case "update":
                onUpdateFailed(receivedProduct);
                break;
            case "delete":
                onDeleteFailed(receivedProduct);
                break;
        }
    }

    public void onAddFailed(OrderServiceProduct orderServiceProduct){
        productService.deleteProductById(orderServiceProduct.getId());
    }

    public void onUpdateFailed(OrderServiceProduct orderServiceProduct){
        var oldCategory = categoryService.getCategoryById(orderServiceProduct.getCategoryId());
        var oldSubcategory = subcategoryService.getSubcategoryById(orderServiceProduct.getSubcategoryId());

        var oldProduct = Product.builder()
                .id(orderServiceProduct.getId())
                .name(orderServiceProduct.getName())
                .description(orderServiceProduct.getDescription())
                .quantity(orderServiceProduct.getQuantity())
                .price(orderServiceProduct.getPrice())
                .reviewSum(orderServiceProduct.getReviewSum())
                .totalReviews(orderServiceProduct.getTotalReviews())
                .category(oldCategory)
                .subcategory(oldSubcategory)
                .build();

        productService.createOrUpdateProduct(oldProduct);
    }

    public void onDeleteFailed(OrderServiceProduct orderServiceProduct){
        var oldCategory = categoryService.getCategoryById(orderServiceProduct.getCategoryId());
        var oldSubcategory = subcategoryService.getSubcategoryById(orderServiceProduct.getSubcategoryId());

        var oldProduct = Product.builder()
                .id(orderServiceProduct.getId())
                .name(orderServiceProduct.getName())
                .description(orderServiceProduct.getDescription())
                .quantity(orderServiceProduct.getQuantity())
                .price(orderServiceProduct.getPrice())
                .reviewSum(orderServiceProduct.getReviewSum())
                .totalReviews(orderServiceProduct.getTotalReviews())
                .category(oldCategory)
                .subcategory(oldSubcategory)
                .build();

        productService.createOrUpdateProduct(oldProduct);
    }

}
