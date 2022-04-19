package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Policy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PolicyDTO {

    private Long id;
    private String type;
    private String title;
    private String description;
    private String icon;


    public PolicyDTO(Policy entity){
        this.id = entity.getId();
        this.type = entity.getType();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.icon = entity.getIcon();
    }


}
