package com.example.grocerylist.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchMealException extends ResponseStatusException {

    public NoSuchMealException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "No such meal exists");
    }
}
