package com.mycomp.rsocketapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    private String FirstName,LastName;
    private String City,State;
}
