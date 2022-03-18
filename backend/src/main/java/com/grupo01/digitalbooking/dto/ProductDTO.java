package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Characteristic;
import com.grupo01.digitalbooking.domain.Product;
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
    private List<Long> characteristicsId;
    private String availableDate;

    public ProductDTO(Product entity){

        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.categoryId = entity.getCategory().getId();
        this.characteristicsId = entity.getCharacteristics()==null?null:
                entity.getCharacteristics()
                .stream()
                .map(Characteristic::getId)
                .collect(Collectors.toList());
        this.cityId = entity.getCity().getId();
        this.availableDate = entity.getAvailableDate().toString();
    }
}
