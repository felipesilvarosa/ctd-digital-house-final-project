package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String imageUrl;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Category(CategoryDTO dto) {
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.imageUrl = dto.getImageUrl();
    }

    public Category(Long id) {
        this.id = id;
    }
}
