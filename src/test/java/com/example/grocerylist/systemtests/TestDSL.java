package com.example.grocerylist.systemtests;

import com.example.grocerylist.web.dtos.CreateListDTO;
import com.example.grocerylist.web.dtos.GroceryListDTO;
import com.example.grocerylist.web.dtos.IdDTO;
import com.example.grocerylist.web.dtos.IngredientStateDTO;
import com.example.grocerylist.web.dtos.ItemDTO;
import com.example.grocerylist.web.dtos.MealNameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class TestDSL {

    private final TestRestTemplate rest;

    @Autowired
    public TestDSL(TestRestTemplate rest) {
        this.rest = rest;
    }

    public UUID createList(String name) {
        final ResponseEntity<IdDTO> response = rest.postForEntity("/lists", new CreateListDTO(name), IdDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        UUID receivedId = response.getBody().getId();
        assertThat(receivedId).isNotNull();
        assertThat(response.getHeaders().get("Location")).containsExactly("/lists/" + receivedId);
        return receivedId;
    }

    public void assertListHasName(UUID groceryListId, String name) {
        final ResponseEntity<GroceryListDTO> response = rest.getForEntity("/lists/{listId}", GroceryListDTO.class, groceryListId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo(name);
    }

    public void addMeal(UUID groceryListId, String mealName) {
        final ResponseEntity<Void> response = rest.postForEntity("/lists/{listId}/meals", new MealNameDTO(mealName), Void.class, groceryListId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    public void assertListHasIngredient(UUID groceryListId, int itemCount, int itemIdx, String itemName, String itemAmount) {
        final ResponseEntity<GroceryListDTO> response = rest.getForEntity("/lists/{listId}", GroceryListDTO.class, groceryListId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        final List<ItemDTO> items = response.getBody().getItems();
        assertThat(items).hasSize(itemCount);
        assertThat(items.get(itemIdx).getName()).isEqualTo(itemName);
        assertThat(items.get(itemIdx).isBought()).isFalse();
        assertThat(items.get(itemIdx).getAmounts()).containsExactly(itemAmount);
    }

    public void toggleIngredientBought(UUID groceryListId, String ingredientName, boolean bought) {
        rest.put(
                "/lists/{listId}/ingredients/{ingredientName}/bought",
                new IngredientStateDTO(bought),
                groceryListId,
                ingredientName
        );
    }

    public void assertBoughStates(UUID groceryListId, boolean... firstNStates) {
        final ResponseEntity<GroceryListDTO> response = rest.getForEntity("/lists/{listId}", GroceryListDTO.class, groceryListId);
        final List<ItemDTO> items = response.getBody().getItems();

        for (int i = 0; i < firstNStates.length; i++) {
            assertThat(items.get(i).isBought()).isEqualTo(firstNStates[i]);
        }
    }
}
