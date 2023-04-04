package com.example.grocerylist.systemtests;

import com.example.grocerylist.web.CreateListDTO;
import com.example.grocerylist.web.GroceryListDTO;
import com.example.grocerylist.web.IdDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
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

}
