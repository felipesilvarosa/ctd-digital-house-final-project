package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Utilities;
import com.grupo01.digitalbooking.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UtilitiesDTO {
    private Long id;
    private String name;

    public UtilitiesDTO(Utilities entity){
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
