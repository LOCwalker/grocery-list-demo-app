package com.example.grocerylist.web.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroceryListDTO {

    private long id;
    private String name;
    private List<ItemDTO> items;

}
