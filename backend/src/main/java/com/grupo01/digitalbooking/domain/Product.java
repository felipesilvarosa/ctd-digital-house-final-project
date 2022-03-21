package com.grupo01.digitalbooking.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.grupo01.digitalbooking.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate availableDate;

    @OneToMany(mappedBy = "product")
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToMany
    private List<Characteristic> characteristics;

    public Product(ProductDTO dto){
        this.id = dto.getId();
        this.name = dto.getName();
        this.category = dto.getCategory();
        this.description = dto.getDescription();
        this.city = dto.getCity();
        this.availableDate = dto.getAvailableDate()==null?null:LocalDate.parse(dto.getAvailableDate());
        this.characteristics = dto.getCharacteristics();
    }
}
