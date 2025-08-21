package com.examly.springapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // This will return 400 instead of 500
public class AlreadyVotedException extends RuntimeException {
    public AlreadyVotedException(String message) {
        super(message);
    }
    
    public AlreadyVotedException(String message, Throwable cause) {
        super(message, cause);
    }
}