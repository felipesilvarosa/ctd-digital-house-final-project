package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Category;
import com.grupo01.digitalbooking.domain.Products;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private List<Products> products;

    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.imageUrl = entity.getImageUrl();
    }
}
