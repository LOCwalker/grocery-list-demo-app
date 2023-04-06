package com.example.grocerylist.web.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroceryListDTO {

    private UUID id;
    private String name;
    private List<ItemDTO> items;

}
