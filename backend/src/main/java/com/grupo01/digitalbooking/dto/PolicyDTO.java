package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Policy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDTO {

    private String title;
    private Integer order;
    private List<PolicyDescriptionDTO> descriptions;

    public PolicyDTO(Policy entity){
        this.title = entity.getTitle();
        switch (entity.getType()){
            case "rules" : order = 0;break;
            case "safety" : order = 1;break;
            case "canceling" : order = 2;break;
        }
        this.descriptions = entity.getPolicyDescriptions()
                .stream()
                .map(PolicyDescriptionDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "PolicyDTO{" +
                "title='" + title + '\'' +
                ", descriptions=" + descriptions +
                '}';
    }
}
