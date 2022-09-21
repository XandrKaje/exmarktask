package com.upd.exmarktask.model.exception;

import lombok.Getter;

@Getter
public class DocumentException extends RuntimeException implements OrdinaryException {

    private final String message;

    public DocumentException(String message) {
        this.message = message;
    }
}