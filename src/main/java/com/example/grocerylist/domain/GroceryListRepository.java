package com.example.grocerylist.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroceryListRepository extends CrudRepository<GroceryListEntity, Long> {
}
