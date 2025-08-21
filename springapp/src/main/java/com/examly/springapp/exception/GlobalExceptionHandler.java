package com.examly.springapp.exception;

import com.examly.springapp.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ApiResponse errorResponse = new ApiResponse(false, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> unauthorizedException(UnauthorizedException ex, WebRequest request) {
        ApiResponse errorResponse = new ApiResponse(false, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(AlreadyVotedException.class)
    public ResponseEntity<?> alreadyVotedException(AlreadyVotedException ex, WebRequest request) {
        ApiResponse errorResponse = new ApiResponse(false, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // Returns 400 instead of 500
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ApiResponse errorResponse = new ApiResponse(false, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ApiResponse errorResponse = new ApiResponse(false, "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}