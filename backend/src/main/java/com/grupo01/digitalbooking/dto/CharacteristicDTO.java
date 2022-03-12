package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Characteristic;
import com.grupo01.digitalbooking.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CharacteristicDTO {
    private Long id;
    private String name;
    private String icon;
    private List<Product> products;

    public CharacteristicDTO(Characteristic entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.icon = entity.getIcon();
        this.products = entity.getProducts();
    }
}
