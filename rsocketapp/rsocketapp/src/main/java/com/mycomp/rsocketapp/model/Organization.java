package com.mycomp.rsocketapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Organization {
    private int index;
    private String name;
    private String website;
    private String country;
    private int numberOfEmployees;
}
