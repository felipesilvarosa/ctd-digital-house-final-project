package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Products;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductsDTO {
    private Long id;
    private String name;
    private String description;

    public ProductsDTO(Products entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
    }
}
