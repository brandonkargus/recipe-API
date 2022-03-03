package com.api.recipeapi.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(InvalidFormatException.class)
//    public ResponseEntity<String> handleJsonException(InvalidFormatException e) {
//
//        return ResponseEntity.badRequest().body(e.getMessage());
//
//    }

//    @ExceptionHandler(InvalidFormatException.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ExceptionResponse handleJsonException(InvalidFormatException e) {
//
//        return new ExceptionResponse(e.getMessage());
//
//    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ExceptionResponse> handleJsonException(InvalidFormatException e) {           //TODO future, create methods for all project exceptions

        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));

    }

}
