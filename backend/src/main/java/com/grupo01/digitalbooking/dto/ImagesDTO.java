package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Images;
import com.grupo01.digitalbooking.domain.Products;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImagesDTO {
    private Long id;
    private String title;
    private String url;
    private Products products;

    private ImagesDTO(Images entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.url = entity.getUrl();
        this.products = entity.getProducts();
    }
}
