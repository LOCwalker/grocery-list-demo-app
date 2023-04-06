package com.example.grocerylist.web.dtos;

import jakarta.validation.constraints.NotNull;

public class IngredientStateDTO {

    @NotNull
    private Boolean bought;

    public IngredientStateDTO(Boolean bought) {
        this.bought = bought;
    }

    public IngredientStateDTO() {
    }

    public Boolean getBought() {
        return bought;
    }
}
