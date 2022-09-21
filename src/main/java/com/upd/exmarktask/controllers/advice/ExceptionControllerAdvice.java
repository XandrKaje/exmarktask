package com.upd.exmarktask.controllers.advice;

import com.upd.exmarktask.model.exception.DocumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(DocumentException.class)
    public ResponseEntity<?> handleException(DocumentException exception) {
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}


