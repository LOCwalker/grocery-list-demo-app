package com.example.grocerylist.web.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemDTO {

    private String name;
    private List<String> amounts;
    private boolean bought;

}
