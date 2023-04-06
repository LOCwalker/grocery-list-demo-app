package com.example.grocerylist.domain;

import com.example.grocerylist.infra.MealDbClient;
import com.example.grocerylist.infra.MealListDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GroceryListService {

    private final GroceryListRepository groceryListRepository;
    private final MealDbClient mealDbClient;

    @Autowired
    public GroceryListService(GroceryListRepository groceryListRepository, MealDbClient mealDbClient) {
        this.groceryListRepository = groceryListRepository;
        this.mealDbClient = mealDbClient;
    }

    public UUID createList(String name) {
        final GroceryListEntity groceryListEntity = new GroceryListEntity(null, name);
        groceryListRepository.save(groceryListEntity);
        return groceryListEntity.getId();
    }

    public GroceryListEntity getList(UUID id) {
        return groceryListRepository.findById(id)
                .orElseThrow(GroceryListNotFoundException::new);
    }

    public void addMealToList(UUID listId, String mealName) {
        final GroceryListEntity groceryListEntity = groceryListRepository.findById(listId).orElseThrow(GroceryListNotFoundException::new);
        final MealListDTO meal = mealDbClient.findMeal(mealName);
        if (meal.getMeals() == null || meal.getMeals().isEmpty()) {
            throw new NoSuchMealException();
        }
        final JsonNode firstMeal = meal.getMeals().get(0);
        final MealEntity mealEntity = groceryListEntity.addMeal(firstMeal.get("strMeal").asText());

        boolean hasMore = true;
        for (int i = 1; hasMore; i++) {
            final JsonNode ingredientName = firstMeal.get("strIngredient" + i);
            if (ingredientName != null && ingredientName.asText().length() > 0) {
                final JsonNode measure = firstMeal.get("strMeasure" + i);
                mealEntity.addIngredient(ingredientName.asText(), measure.asText());
            } else {
                hasMore = false;
            }
        }

        groceryListRepository.save(groceryListEntity);
    }

    public void toggleBought(UUID listId, String ingredientName, boolean bought) {
        final GroceryListEntity groceryListEntity = groceryListRepository.findById(listId).orElseThrow(GroceryListNotFoundException::new);

        groceryListEntity.getMealIngredients()
                .stream()
                .filter(ingredient -> ingredient.getName().equals(ingredientName))
                .forEach(ingredient -> ingredient.setBought(bought));

        groceryListRepository.save(groceryListEntity);
    }
}
