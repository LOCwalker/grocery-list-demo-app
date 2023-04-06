package com.example.grocerylist.systemtests;

import com.example.grocerylist.web.CreateListDTO;
import com.example.grocerylist.web.GroceryListDTO;
import com.example.grocerylist.web.IdDTO;
import com.example.grocerylist.web.IngredientStateDTO;
import com.example.grocerylist.web.ItemDTO;
import com.example.grocerylist.web.MealNameDTO;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@WireMockTest(httpPort = 8665)
class GroceryListSystemTests {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void createList() {
        final ResponseEntity<IdDTO> creationResponse = rest.postForEntity("/lists", new CreateListDTO("nombre"), IdDTO.class);
        assertThat(creationResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        long receivedId = creationResponse.getBody().getId();
        assertThat(receivedId).isGreaterThan(0);

        final ResponseEntity<GroceryListDTO> retrievalResponse = rest.getForEntity("/lists/{listId}", GroceryListDTO.class, receivedId);
        assertThat(retrievalResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(retrievalResponse.getBody().getName()).isEqualTo("nombre");
    }

    @Test
    void addMealToList() {
        WireMock.stubFor(
                WireMock.get("/1/search.php?s=Arrabiata")
                    .willReturn(WireMock.aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBodyFile("arrabiata.json")
                    )
        );

        final ResponseEntity<IdDTO> creationResponse = rest.postForEntity("/lists", new CreateListDTO("ignored"), IdDTO.class);
        long receivedId = creationResponse.getBody().getId();

        final ResponseEntity<Void> postMealResponse = rest.postForEntity("/lists/{listId}/meals", new MealNameDTO("Arrabiata"), Void.class, receivedId);
        assertThat(postMealResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        final ResponseEntity<GroceryListDTO> retrievalResponse = rest.getForEntity("/lists/{listId}", GroceryListDTO.class, receivedId);
        assertThat(retrievalResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        final List<ItemDTO> items = retrievalResponse.getBody().getItems();
        assertThat(items).hasSize(8);
        assertThat(items.get(0).getName()).isEqualTo("penne rigate");
        assertThat(items.get(0).isBought()).isFalse();
        assertThat(items.get(0).getAmounts()).containsExactly("1 pound");

        rest.put(
                "/lists/{listId}/ingredients/{ingredientName}/bought",
                new IngredientStateDTO(true),
                receivedId,
                "penne rigate"
        );

        final ResponseEntity<GroceryListDTO> retrievalResponseAfterToggle = rest.getForEntity("/lists/{listId}", GroceryListDTO.class, receivedId);
        assertThat(retrievalResponseAfterToggle.getBody().getItems().get(0).isBought()).isTrue();
        assertThat(retrievalResponseAfterToggle.getBody().getItems().get(1).isBought()).isFalse();
    }

}
