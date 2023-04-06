package com.example.grocerylist.systemtests;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
        final long listId = dsl.createList("nombre");
        dsl.assertListHasName(listId, "nombre");
    }

    @Test
    void addMealToList() {
        mealDbFaker.fakeArrabiata();
        final long listId = dsl.createList("ignored");

        dsl.addMeal(listId, "Arrabiata");
        dsl.assertListHasIngredient(listId, 8, 0, "penne rigate", "1 pound");
        dsl.assertListHasIngredient(listId, 8, 6, "basil", "6 leaves");
    }

    @Test
    void toggleIngredientState() {
        mealDbFaker.fakeArrabiata();
        final long listId = dsl.createList("ignored");
        dsl.addMeal(listId, "Arrabiata");

        dsl.assertBoughStates(listId, false, false);
        dsl.toggleIngredientBought(listId, "penne rigate", true);
        dsl.assertBoughStates(listId, true, false);
    }

}
