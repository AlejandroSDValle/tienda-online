package com.msv.product.exception;

import com.libs.msvc.commons.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<?> objectNotFoundException(ObjectNotFoundException exception){

        ApiResponse error = new ApiResponse();
        error.setMessage("The requested object was not found.");
        error.setBackedMessage(exception.getLocalizedMessage());
        error.setTime(LocalDateTime.now());
        error.setHttpCode(404);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
