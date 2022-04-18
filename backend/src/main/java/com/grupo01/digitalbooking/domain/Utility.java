package com.grupo01.digitalbooking.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_utilities")
public class Utility {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(name = "tb_products_utilities",
            joinColumns = {@JoinColumn(name = "utility_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private List<Product> products;
    public Utility(Long id) {
        this.id = id;
    }

}
