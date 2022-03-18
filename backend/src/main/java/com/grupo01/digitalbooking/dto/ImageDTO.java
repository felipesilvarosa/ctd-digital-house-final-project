package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Image;
import com.grupo01.digitalbooking.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImageDTO {
    private Long id;
    private String title;
    private String url;
    private Long productId;

    private ImageDTO(Image entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.url = entity.getUrl();
        this.productId = entity.getProduct()==null?null:entity.getProduct().getId();
    }
}
