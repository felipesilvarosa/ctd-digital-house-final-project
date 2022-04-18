package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private Long id;
    private Integer productCount;
    private String title;
    private String description;
    private String imageUrl;

    public CategoryDTO(Category entity,Integer productCount) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.imageUrl = entity.getImageUrl();
        this.productCount = productCount;
    }
    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.imageUrl = entity.getImageUrl();
    }
}
