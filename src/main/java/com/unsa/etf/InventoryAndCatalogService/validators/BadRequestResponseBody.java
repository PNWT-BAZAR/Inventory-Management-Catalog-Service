package com.unsa.etf.InventoryAndCatalogService.validators;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor  //zasto ne radi ???
@NoArgsConstructor
public class BadRequestResponseBody {
    private ErrorCode error;
    private String message;

//    public BadRequestResponseBody(ErrorCode validation, String s) {
////        this.error = error;
////        this.message = s;
////    }

    public enum ErrorCode {
        VALIDATION,
        NOT_FOUND,
        ALREADY_EXISTS;
    }
}