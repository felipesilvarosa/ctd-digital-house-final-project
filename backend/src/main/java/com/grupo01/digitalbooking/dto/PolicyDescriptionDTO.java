package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.PolicyDescription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDescriptionDTO {

    private String icon;
    private String description;

    public PolicyDescriptionDTO(PolicyDescription entity){
        this.icon = entity.getIcon();
        this.description = entity.getDescription();
    }

}
