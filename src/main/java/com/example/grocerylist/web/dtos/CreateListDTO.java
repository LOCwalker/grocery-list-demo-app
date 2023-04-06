package com.example.grocerylist.web.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateListDTO {

    @Size(max = 50, min = 1)
    private String name;

}
