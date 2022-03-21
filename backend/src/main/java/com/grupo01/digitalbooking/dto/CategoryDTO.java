package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Category;
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
public class CategoryDTO {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;

    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.imageUrl = entity.getImageUrl();
    }
}
