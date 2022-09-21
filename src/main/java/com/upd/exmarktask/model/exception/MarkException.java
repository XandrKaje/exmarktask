package com.upd.exmarktask.model.exception;

import lombok.Getter;

@Getter
public class MarkException extends RuntimeException implements OrdinaryException {

    private final String message;

    public MarkException(String message) {
        this.message = message;
    }
}
