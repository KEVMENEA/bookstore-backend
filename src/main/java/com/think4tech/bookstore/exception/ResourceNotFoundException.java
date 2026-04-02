package com.think4tech.bookstore.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException{

    public ResourceNotFoundException(String resourceName, Long id) {
        super(HttpStatus.NOT_FOUND, String.format("%s not found with id: %d", resourceName, id));
    }


        public ResourceNotFoundException(String resourceName, Object value) {
            super(HttpStatus.NOT_FOUND, String.format("%s not found with id: %s", resourceName, value));
        }

}
