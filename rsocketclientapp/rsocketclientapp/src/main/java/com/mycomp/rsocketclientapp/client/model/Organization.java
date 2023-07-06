package com.mycomp.rsocketclientapp.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Organization {
    private Integer index;
    private String name;
    private String website;
    private String country;
    private Integer numberOfEmployees;
}
