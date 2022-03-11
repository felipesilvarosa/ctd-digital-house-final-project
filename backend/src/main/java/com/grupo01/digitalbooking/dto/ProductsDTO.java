package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Category;
import com.grupo01.digitalbooking.domain.Characteristics;
import com.grupo01.digitalbooking.domain.Cities;
import com.grupo01.digitalbooking.domain.Products;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductsDTO {
    private Long id;
    private String name;
    private String description;
    private Category category;
    private Cities cities;
    private List<Characteristics> characteristics;

    public ProductsDTO(Products entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.category = entity.getCategory();
        this.cities = entity.getCities();
        this.characteristics = entity.getCharacteristics();
    }
}
