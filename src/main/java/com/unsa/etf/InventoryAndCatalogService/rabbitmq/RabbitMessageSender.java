package com.unsa.etf.InventoryAndCatalogService.rabbitmq;

import com.unsa.etf.InventoryAndCatalogService.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.unsa.etf.InventoryAndCatalogService.rabbitmq.RabbitConfig.*;

@RequiredArgsConstructor
@Component
public class RabbitMessageSender {
    private final RabbitTemplate rabbitTemplate;

    public void notifyOrderServiceOfChange(Product product, String operation){
        var orderServiceProduct = mapInventoryProductToOrdersProduct(product);
        var rabbitProduct = new ProductRabbitSenderModel(orderServiceProduct, operation);
        rabbitTemplate.convertAndSend(topicExchangeName, "foo.forward.#", rabbitProduct);
    }

    private OrderServiceProduct mapInventoryProductToOrdersProduct (Product product){
        return new OrderServiceProduct(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getQuantity(),
                product.getPrice(),
                product.getReviewSum(),
                product.getTotalReviews(),
                product.getCategory().getId(),
                product.getSubcategory().getId());
    }
}
