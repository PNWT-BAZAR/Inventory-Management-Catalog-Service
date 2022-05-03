package com.unsa.etf.InventoryAndCatalogService.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ObjectResponse<ObjectType> {
    private int statusCode;
    private ObjectType object;
    private BadRequestResponseBody error;

}
