package com.grupo01.digitalbooking.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "tb_policies")
@NoArgsConstructor
public class Policy {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String type;
    private String title;
    private String icon;
    private String description;
    @ManyToMany
    @JoinTable(name = "tb_products_policies",
            joinColumns = {@JoinColumn(name = "policy_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private List<Product> products;

    public Policy(Long id) {
        this.id = id;
    }
}
