package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Policy;
import com.grupo01.digitalbooking.domain.PolicyDescription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDTO {

    private String title;
    private List<PolicyDescription> description;

    private PolicyDTO(Policy entity){
        this.title = entity.getTitle();
        this.description = entity.getDescription();
    }

}
