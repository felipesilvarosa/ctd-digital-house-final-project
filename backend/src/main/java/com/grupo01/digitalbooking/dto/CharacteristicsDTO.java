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

    public CharacteristicsDTO(Characteristics entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.icon = entity.getIcon();
    }
}
