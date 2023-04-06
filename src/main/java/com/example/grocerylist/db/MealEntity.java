package com.example.grocerylist.db;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meals")
public class MealEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "meals_key")
    private List<IngredientEntity> ingredients = new ArrayList<>();

    public MealEntity(String name) {
        this.name = name;
    }

    public MealEntity() {
    }

    public void addIngredient(String name, String measure) {
        ingredients.add(new IngredientEntity(name, measure));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<IngredientEntity> getIngredients() {
        return ingredients;
    }
}
