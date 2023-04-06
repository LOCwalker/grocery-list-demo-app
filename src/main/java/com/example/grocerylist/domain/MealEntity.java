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
import java.util.List;

@Entity
@Table(name = "meals")
@NoArgsConstructor
@Getter
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

    public void addIngredient(String name, String measure) {
        ingredients.add(new IngredientEntity(name, measure));
    }

}
