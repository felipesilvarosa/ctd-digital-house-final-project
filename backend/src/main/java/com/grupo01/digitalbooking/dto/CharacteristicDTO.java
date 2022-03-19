package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Characteristic;
import com.grupo01.digitalbooking.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CharacteristicDTO {
    private Long id;
    private String name;
    private String icon;
    private List<Long> productsId;

    public CharacteristicDTO(Characteristic entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.icon = entity.getIcon();
        this.productsId = entity.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList());
    }
}
