package com.example.grocerylist.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MealDbClient {

    private RestTemplate restTemplate;
    private String mealDbBaseUrl;

    @Autowired
    public MealDbClient(RestTemplate restTemplate, @Value("${meal-db-base-url}") String mealDbBaseUrl) {
        this.restTemplate = restTemplate;
        this.mealDbBaseUrl = mealDbBaseUrl;
    }

    public MealListDTO findMeal(String searchTerm) {
        return restTemplate.getForObject(mealDbBaseUrl + "/1/search.php?s={searchTerm}", MealListDTO.class, searchTerm);
    }
}
