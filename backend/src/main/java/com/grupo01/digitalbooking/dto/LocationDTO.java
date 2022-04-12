package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Location;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LocationDTO {
    private Long id;
    private String name;
    private String city;
    private String country;

    public LocationDTO(Location entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.country = entity.getCountry();
    }
}
