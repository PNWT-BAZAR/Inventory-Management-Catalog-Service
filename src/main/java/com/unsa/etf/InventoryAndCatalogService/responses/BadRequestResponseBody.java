package com.unsa.etf.InventoryAndCatalogService.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor  //zasto ne radi ???
@NoArgsConstructor
public class BadRequestResponseBody {
    private ErrorCode error;
    private String message;

    public enum ErrorCode {
        VALIDATION,
        NOT_FOUND,
        ALREADY_EXISTS;
    }
}