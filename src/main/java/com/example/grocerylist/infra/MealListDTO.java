package com.example.grocerylist.infra;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

import java.util.List;

@Getter
public class MealListDTO {

    // due to the unideal payload structure of themealdb, we have to rely on this unstructured approach here
    private List<JsonNode> meals;

}
