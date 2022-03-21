package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.City;
import com.grupo01.digitalbooking.domain.Product;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CityDTO {
    private Long id;
    private String name;
    private String country;

    public CityDTO(City entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.country = entity.getCountry();
    }
}
