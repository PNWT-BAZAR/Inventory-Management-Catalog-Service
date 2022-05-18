package com.unsa.etf.InventoryAndCatalogService.rabbitmq;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.criterion.Order;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRabbitReceiverModel {
    private OrderServiceProduct product;
    private String operation;
}
