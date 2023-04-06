package com.example.grocerylist.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class MealListDTO {

    // due to the unideal payload structure of themealdb, we have to rely on this unstructured approach here
    private List<JsonNode> meals;

    public List<JsonNode> getMeals() {
        return meals;
    }
}
