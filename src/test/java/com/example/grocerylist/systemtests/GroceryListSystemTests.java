package com.example.grocerylist.systemtests;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@WireMockTest(httpPort = 8665)
class GroceryListSystemTests {

    private final TestDSL dsl;
    private final MealDbFaker mealDbFaker;

    @Autowired
    public GroceryListSystemTests(TestDSL dsl, MealDbFaker mealDbFaker) {
        this.dsl = dsl;
        this.mealDbFaker = mealDbFaker;
    }

    @Test
    void createList() {
        final UUID listId = dsl.createList("nombre");
        dsl.assertListHasName(listId, "nombre");
    }

    @Test
    void addMealToList() {
        mealDbFaker.fakeArrabiata();
        final UUID listId = dsl.createList("ignored");

        dsl.addMeal(listId, "Arrabiata");
        dsl.assertListHasIngredient(listId, 8, 6, "penne rigate", "1 pound");
        dsl.assertListHasIngredient(listId, 8, 1, "basil", "6 leaves");
    }

    @Test
    void toggleIngredientState() {
        mealDbFaker.fakeArrabiata();
        final UUID listId = dsl.createList("ignored");
        dsl.addMeal(listId, "Arrabiata");

        dsl.assertBoughStates(listId, false, false);
        dsl.toggleIngredientBought(listId, "basil", true);
        dsl.assertBoughStates(listId, false, true);
    }

}
