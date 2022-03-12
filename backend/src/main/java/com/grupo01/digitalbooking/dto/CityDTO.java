package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.City;
import com.grupo01.digitalbooking.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityDTO {
    private Long id;
    private String name;
    private String country;
    private List<Product> products;

    public CityDTO(City entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.country = entity.getCountry();
        this.products = entity.getProducts();
    }
}
