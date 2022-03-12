package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Cities;
import com.grupo01.digitalbooking.domain.Products;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CitiesDTO {
    private Long id;
    private String name;
    private String country;
    private List<Products> products;

    public CitiesDTO(Cities entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.country = entity.getCountry();
        this.products = entity.getProducts();
    }
}
