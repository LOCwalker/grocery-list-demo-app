package com.example.grocerylist.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "grocery_lists")
@NoArgsConstructor
public class GroceryListEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Column(name = "name")
    @Getter
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "grocery_lists_key")
    private List<MealEntity> meals = new ArrayList<>();

    public GroceryListEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public MealEntity addMeal(String name) {
        final MealEntity meal = new MealEntity(name);
        this.meals.add(meal);
        return meal;
    }

    public List<IngredientEntity> getMealIngredients() {
        return meals.stream()
                .map(MealEntity::getIngredients)
                .flatMap(Collection::stream)
                .toList();
    }
}
