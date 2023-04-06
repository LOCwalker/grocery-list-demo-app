package com.example.grocerylist.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface GroceryListRepository extends CrudRepository<GroceryListEntity, UUID> {
}
