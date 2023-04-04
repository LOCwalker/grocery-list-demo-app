package com.example.grocerylist.web;

import com.example.grocerylist.service.GroceryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class GroceryListController {

    private GroceryListService groceryListService;

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
        return groceryListService.getList(listId)
                .map(list -> new GroceryListDTO(list.getId(), list.getName()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No grocery list with this id exists"));
    }
}
