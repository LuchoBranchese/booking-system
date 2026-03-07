package com.lbranchese.BookingSystem.infrastructure.api;

import com.lbranchese.BookingSystem.application.command.exception.ResourceAlreadyReservedException;
import com.lbranchese.BookingSystem.application.command.exception.ResourceInactiveException;
import com.lbranchese.BookingSystem.application.command.exception.ResourceNotFoundException;
import com.lbranchese.BookingSystem.application.command.exception.UserBlockedException;
import com.lbranchese.BookingSystem.application.command.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("USER_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("RESOURCE_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(UserBlockedException.class)
    public ResponseEntity<ErrorResponse> handleUserBlocked(UserBlockedException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("USER_BLOCKED", ex.getMessage()));
    }

    @ExceptionHandler(ResourceInactiveException.class)
    public ResponseEntity<ErrorResponse> handleResourceInactive(ResourceInactiveException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("RESOURCE_INACTIVE", ex.getMessage()));
    }

    @ExceptionHandler(ResourceAlreadyReservedException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyReserved(ResourceAlreadyReservedException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("RESOURCE_ALREADY_RESERVED", ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleGeneric(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_ERROR", "Unexpected error: " + ex.getMessage()));
    }
}

