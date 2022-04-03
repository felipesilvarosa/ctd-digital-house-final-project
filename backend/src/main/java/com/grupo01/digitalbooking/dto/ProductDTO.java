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
    private Long categoryId;
    private Long cityId;
    private Long userRatings;
    private List<Long> characteristicIds;
    private List<Long> imageIds;
    private List<LocalDate> availableDates;

    public ProductDTO(Product entity){

        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.categoryId = entity.getCategory().getId();
        this.cityId = entity.getCity().getId();
        this.availableDates = entity.getAvailableDates()
                .stream()
                .map(AvailableDate::getValue)
                .collect(Collectors.toList());
        this.imageIds = entity.getImages()
                .stream()
                .map(Image::getId)
                .collect(Collectors.toList());
        this.characteristicIds = entity.getCharacteristics()
                .stream()
                .map(Characteristic::getId)
                .collect(Collectors.toList());
    }



}
