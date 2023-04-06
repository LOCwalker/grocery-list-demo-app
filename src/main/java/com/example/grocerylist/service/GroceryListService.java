package com.example.grocerylist.service;

import com.example.grocerylist.db.GroceryListEntity;
import com.example.grocerylist.db.GroceryListRepository;
import com.example.grocerylist.db.MealEntity;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GroceryListService {

    private GroceryListRepository groceryListRepository;
    private MealDbClient mealDbClient;

    @Autowired
    public GroceryListService(GroceryListRepository groceryListRepository, MealDbClient mealDbClient) {
        this.groceryListRepository = groceryListRepository;
        this.mealDbClient = mealDbClient;
    }

    public long createList(String name) {
        final GroceryListEntity groceryListEntity = new GroceryListEntity(null, name);
        groceryListRepository.save(groceryListEntity);
        return groceryListEntity.getId();
    }

    public GroceryListEntity getList(long id) {
        return groceryListRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No grocery list with this id exists"));
    }

    public void addMealToList(long listId, String mealName) {
        final GroceryListEntity groceryListEntity = groceryListRepository.findById(listId).orElseThrow(RuntimeException::new);
        final MealListDTO meal = mealDbClient.findMeal(mealName);
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

    public void toggleBought(long listId, String ingredientName, boolean bought) {
        final GroceryListEntity groceryListEntity = groceryListRepository.findById(listId).orElseThrow(RuntimeException::new);

        groceryListEntity.getMealIngredients()
                .stream()
                .filter(ingredient -> ingredient.getName().equals(ingredientName))
                .forEach(ingredient -> ingredient.setBought(bought));

        groceryListRepository.save(groceryListEntity);
    }
}
