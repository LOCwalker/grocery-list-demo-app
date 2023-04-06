package com.example.grocerylist.web;

import jakarta.validation.constraints.NotEmpty;

public class MealNameDTO {

    @NotEmpty
    private String mealName;

    public MealNameDTO(String mealName) {
        this.mealName = mealName;
    }

    public MealNameDTO() {
    }

    public String getMealName() {
        return mealName;
    }
}
