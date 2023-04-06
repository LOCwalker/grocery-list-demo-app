package com.example.grocerylist.web;

import com.example.grocerylist.domain.GroceryListEntity;
import com.example.grocerylist.domain.GroceryListService;
import com.example.grocerylist.domain.IngredientEntity;
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

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
        // given a list of ingredients where the same ingredient can occur multiple times, we want to group these entries
        // input: [{name: "rice", measure: "1 cup"}, {name: "rice", measure: "1/2 pack"}]
        // output: [{"name": "rice", "amounts": ["1 cup", "1/2 pack"]}]
        final Map<String, List<IngredientEntity>> groupedIngredients = listEntity.getMealIngredients()
                .stream()
                .collect(Collectors.groupingBy(IngredientEntity::getName));
        final List<ItemDTO> items = groupedIngredients
                .entrySet()
                .stream()
                .map(entry -> new ItemDTO(
                        entry.getKey(),
                        entry.getValue().stream().map(IngredientEntity::getMeasure).toList(),
                        entry.getValue().get(0).isBought())
                )
                .sorted(Comparator.comparing(ItemDTO::getName))
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
