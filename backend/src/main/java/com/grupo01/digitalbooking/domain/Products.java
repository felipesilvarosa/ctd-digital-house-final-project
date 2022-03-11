package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.ProductsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "products")
    private List<Images> images;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "cities_id")
    private Cities cities;

    @ManyToMany
    private List<Characteristics> characteristics;

    public Products (ProductsDTO dto){
        this.id = dto.getId();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.category = dto.getCategory();
        this.cities = dto.getCities();
        this.characteristics = dto.getCharacteristics();
    }

}
