package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String url;

    @ManyToOne
    @JoinColumn(name = "products_id")
    private Product product;

    public Image(ImageDTO dto){
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.url = dto.getUrl();
        this.product = dto.getProduct();
    }
}
