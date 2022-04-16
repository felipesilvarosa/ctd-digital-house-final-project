package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Image;
import com.grupo01.digitalbooking.domain.Product;
import com.grupo01.digitalbooking.domain.Utility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ProductDetailedDTO {

    private Long id;
    private String title;
    private String description;
    private String category;
    private String destination;
    private Integer stars;
    private Integer rating;
    private Double longitude;
    private Double latitude;
    private List<String> images;
    private Set<LocalDate> unavailable;
    private List<String> utilities;
    private Map<String, PolicyDTO> policies;


    public ProductDetailedDTO(Product entity){

        this.id = entity.getId();
        this.title = entity.getName();
        this.description = entity.getDescription();
        this.category = entity.getCategory().getTitle();
        this.stars = entity.getStars();
        this.rating = entity.getRating();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.unavailable = entity.getUnavailableDates();
        this.destination = entity.getDestination().getCity()+", "+
                entity.getDestination().getCountry();
        this.images = entity.getImages()
                .stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());
        this.policies = Map.of(
                entity.getPolicies().get(0).getType(),new PolicyDTO(entity.getPolicies().get(0)),
                entity.getPolicies().get(1).getType(),new PolicyDTO(entity.getPolicies().get(1)),
                entity.getPolicies().get(2).getType(),new PolicyDTO(entity.getPolicies().get(2))
        );
        this.utilities = entity.getUtilities()
                .stream()
                .map(Utility::getName)
                .collect(Collectors.toList());
    }


}