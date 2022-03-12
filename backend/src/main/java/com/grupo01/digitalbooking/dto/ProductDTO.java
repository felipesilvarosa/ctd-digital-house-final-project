package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Category;
import com.grupo01.digitalbooking.domain.Characteristic;
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
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Category category;
    private City city;
    private List<Characteristic> characteristics;

    public ProductDTO(Product entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.category = entity.getCategory();
        this.city = entity.getCity();
        this.characteristics = entity.getCharacteristics();
    }
}
