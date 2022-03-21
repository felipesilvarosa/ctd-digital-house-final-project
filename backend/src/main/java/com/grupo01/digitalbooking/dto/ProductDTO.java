package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<Image> images;
    private String availableDate;

    public ProductDTO(Product entity){

        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.category = entity.getCategory();
        this.characteristics = entity.getCharacteristics();
        this.city = entity.getCity();
        this.availableDate = entity.getAvailableDate().toString();
        this.images = entity.getImages();
    }
}
