package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.CharacteristicDTO;
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
@Table(name = "tb_characteristics")
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String icon;

    @ManyToMany(mappedBy = "characteristics")
    private List<Product> products;

    public Characteristic(CharacteristicDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.icon = dto.getIcon();
        this.products = dto.getProducts().stream().map(Product::new).collect(Collectors.toList());
    }
}
