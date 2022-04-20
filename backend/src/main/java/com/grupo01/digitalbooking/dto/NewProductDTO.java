package com.grupo01.digitalbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewProductDTO {

    private Long id;
    private String name;
    private String description;
    private String address;
    private Integer stars;
    private Integer rating;
    private Long categoryId;
    private List<String> utilitiesNames;
    private List<Long> policiesIds;

}
