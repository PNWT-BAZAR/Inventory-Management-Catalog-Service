package com.unsa.etf.InventoryAndCatalogService.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRabbitSenderModel {
    private OrderServiceProduct product;
    private String operation;
}
