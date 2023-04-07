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
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
public class GroceryListController {

    private final GroceryListService groceryListService;

    @Autowired
    public GroceryListController(GroceryListService groceryListService) {
        this.groceryListService = groceryListService;
    }

    @Operation(summary = "Creates a new grocery list")
    @PostMapping("/lists")
    public ResponseEntity<IdDTO> createList(@RequestBody @Validated CreateListDTO dto) throws URISyntaxException {
        final UUID id = groceryListService.createList(dto.getName());
        return ResponseEntity.created(new URI("/lists/" + id)).body(new IdDTO(id));
    }

    @Operation(summary = "Retrieves a grocery list")
    @GetMapping("/lists/{listId}")
    public GroceryListDTO getList(@PathVariable("listId") UUID listId) {
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

    @Operation(summary = "Adds the ingredients of the specified meal to the grocery list")
    @PostMapping("/lists/{listId}/meals")
    public void addMeal(@PathVariable("listId") UUID listId, @RequestBody @Validated MealNameDTO mealNameDTO) {
        groceryListService.addMealToList(listId, mealNameDTO.getMealName());
    }

    @Operation(summary = "Toggles the 'bought' state of an ingredient on the grocery list")
    @PutMapping("/lists/{listId}/ingredients/{ingredientName}/bought")
    public void toggleBought(
            @PathVariable("listId") UUID listId,
            @PathVariable("ingredientName") String ingredientName,
            @RequestBody @Validated IngredientStateDTO stateDTO) {
        groceryListService.toggleBought(listId, ingredientName, stateDTO.getBought());
    }
}
