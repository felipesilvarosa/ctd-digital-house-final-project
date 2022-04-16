package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Destination;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DestinationDTO {
    private Long id;
    private String city;
    private String country;

    public DestinationDTO(Destination entity){
        this.id = entity.getId();
        this.city = entity.getCity();
        this.country = entity.getCountry();
    }
}
