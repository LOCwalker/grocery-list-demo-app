package com.example.grocerylist.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GroceryListNotFoundException extends ResponseStatusException {

    public GroceryListNotFoundException() {
        super(HttpStatus.NOT_FOUND, "No grocery list with this id exists");
    }
}
