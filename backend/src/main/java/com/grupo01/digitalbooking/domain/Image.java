package com.grupo01.digitalbooking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_images")
public class Image {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    private String url;

    @JsonIgnore
    @ManyToOne(cascade = REFRESH)
    @JoinColumn(name = "product_id")
    private Product product;

    public Image(Long id){
        this.id = id;
    }
}
