package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Characteristics;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CharacteristicsDTO {
    private Long id;
    private String name;
    private String icon;

    public CharacteristicsDTO(Characteristics characteristics){
        this.id = characteristics.getId();
        this.name = characteristics.getName();
        this.icon = characteristics.getIcon();
    }
}
