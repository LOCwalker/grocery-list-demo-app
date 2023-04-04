package com.example.grocerylist.service;

import com.example.grocerylist.db.GroceryListEntity;
import com.example.grocerylist.db.GroceryListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroceryListService {

    private GroceryListRepository groceryListRepository;

    @Autowired
    public GroceryListService(GroceryListRepository groceryListRepository) {
        this.groceryListRepository = groceryListRepository;
    }

    public long createList(String name) {
        final GroceryListEntity groceryListEntity = new GroceryListEntity(0, name);
        groceryListRepository.save(groceryListEntity);
        return groceryListEntity.getId();
    }

    public Optional<GroceryListEntity> getList(long id) {
        return groceryListRepository.findById(id);
    }
}
