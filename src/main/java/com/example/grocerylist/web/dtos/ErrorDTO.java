package com.example.grocerylist.web.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorDTO {

    private String message;
    private int status;

}
