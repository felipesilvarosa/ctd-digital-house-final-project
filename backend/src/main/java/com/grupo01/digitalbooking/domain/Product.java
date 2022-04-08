package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.NewProductDTO;
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
    private Integer stars;
    private Integer rating;
    private Double latitude;
    private Double longitude;

    @ManyToMany
    private List<UnavailableDate> unavailableDates;

    @OneToMany(mappedBy = "product")
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private Location location;

    @ManyToMany
    private List<Utilities> utilities;

    @OneToMany(mappedBy = "product")
    private List<Policy> policies;

    public Product(NewProductDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.category = new Category(dto.getCategoryId());
        this.location = new Location(dto.getCityId());
        this.description = dto.getDescription();
        this.unavailableDates = dto.getAvailableDates() == null ? null : dto.getAvailableDates()
                .stream()
                .map(UnavailableDate::new)
                .collect(Collectors.toList());
        this.utilities = dto.getCharacteristicIds()
                .stream()
                .map(Utilities::new)
                .collect(Collectors.toList());
    }
}
