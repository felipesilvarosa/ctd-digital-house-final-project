package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Cities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CitiesDTO {
    private Long id;
    private String name;
    private String country;

    public CitiesDTO(Cities entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.country = entity.getCountry();
    }
}
