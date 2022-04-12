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
public class NewProductDTO {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private Long cityId;
    private Long userRatings;
    private List<Long> characteristicIds;
    private List<Long> imageIds;
    private List<Long> reservationsIds;

    public NewProductDTO(Product entity){

        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.categoryId = entity.getCategory().getId();
        this.cityId = entity.getLocation().getId();
        this.imageIds = entity.getImages()
                .stream()
                .map(Image::getId)
                .collect(Collectors.toList());
        this.characteristicIds = entity.getUtilities()
                .stream()
                .map(Utilities::getId)
                .collect(Collectors.toList());
    }



}
