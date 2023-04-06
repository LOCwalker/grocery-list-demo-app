package com.example.grocerylist.web;

import com.example.grocerylist.domain.GroceryListEntity;
import com.example.grocerylist.domain.GroceryListService;
import com.example.grocerylist.web.dtos.CreateListDTO;
import com.example.grocerylist.web.dtos.GroceryListDTO;
import com.example.grocerylist.web.dtos.IdDTO;
import com.example.grocerylist.web.dtos.IngredientStateDTO;
import com.example.grocerylist.web.dtos.ItemDTO;
import com.example.grocerylist.web.dtos.MealNameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class GroceryListController {

    private final GroceryListService groceryListService;

    @Autowired
    public GroceryListController(GroceryListService groceryListService) {
        this.groceryListService = groceryListService;
    }

    @PostMapping("/lists")
    public ResponseEntity<IdDTO> createList(@RequestBody @Validated CreateListDTO dto) {
        final long id = groceryListService.createList(dto.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(new IdDTO(id));
    }

    @GetMapping("/lists/{listId}")
    public GroceryListDTO getList(@PathVariable("listId") Long listId) {
        final GroceryListEntity listEntity = groceryListService.getList(listId);
        final List<ItemDTO> items = listEntity.getMealIngredients()
                .stream()
                .map(ingredient -> new ItemDTO(ingredient.getName(), List.of(ingredient.getMeasure()), ingredient.isBought()))
                .toList();
        return new GroceryListDTO(
                listEntity.getId(),
                listEntity.getName(),
                items
        );
    }

    @PostMapping("/lists/{listId}/meals")
    public void addMeal(@PathVariable("listId") Long listId, @RequestBody @Validated MealNameDTO mealNameDTO) {
        groceryListService.addMealToList(listId, mealNameDTO.getMealName());
    }

    @PutMapping("/lists/{listId}/ingredients/{ingredientName}/bought")
    public void toggleBought(
            @PathVariable("listId") Long listId,
            @PathVariable("ingredientName") String ingredientName,
            @RequestBody @Validated IngredientStateDTO stateDTO) {
        groceryListService.toggleBought(listId, ingredientName, stateDTO.getBought());
    }
}
