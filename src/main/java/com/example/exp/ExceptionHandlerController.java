package com.example.exp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(AppBadRequestException.class)
    private ResponseEntity<String> handler(AppBadRequestException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    private ResponseEntity<String> handler(ItemNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }



}
