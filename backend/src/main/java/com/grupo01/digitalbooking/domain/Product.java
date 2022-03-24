package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @ManyToMany
    private List<AvailableDate> availableDates;

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

    public Product(ProductDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.category = new Category(dto.getCategoryId());
        this.city = new City(dto.getCityId());
        this.description = dto.getDescription();
        this.availableDates = dto.getAvailableDates() == null ? null : dto.getAvailableDates()
                .stream()
                .map(AvailableDate::new)
                .collect(Collectors.toList());
        this.characteristics = dto.getCharacteristicIds()
                .stream()
                .map(Characteristic::new)
                .collect(Collectors.toList());
    }
}
