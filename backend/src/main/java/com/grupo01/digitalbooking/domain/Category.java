package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.CategoryDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotEmpty
    private String imageUrl;

    public Category(CategoryDTO dto) {
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.imageUrl = dto.getImageUrl();
    }
}
