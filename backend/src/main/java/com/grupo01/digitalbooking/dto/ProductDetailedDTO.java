package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Image;
import com.grupo01.digitalbooking.domain.Product;
import com.grupo01.digitalbooking.domain.Utilities;
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
    private String location;
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
        this.location = getLocationFromEntity(entity);
        this.images = entity.getImages()
                .stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());
        this.policies = entity.getPolicies()==null||entity.getPolicies().isEmpty()?null:Map.of(
                "rules",new PolicyDTO(entity.getPolicies().get(0)),
                "safety",new PolicyDTO(entity.getPolicies().get(1)),
                "canceling",new PolicyDTO(entity.getPolicies().get(2))
        );
        this.utilities = entity.getUtilities()
                .stream()
                .map(Utilities::getName)
                .collect(Collectors.toList());
    }

    private String getLocationFromEntity(Product entity) {
        if(entity.getDestination()==null) return null;
        String location = "%s, %s";
        if(entity.getDestination().getName()!=null)
            location = String.format(location,entity.getDestination().getName(),entity.getDestination().getCity());
        else
            location = String.format(location,entity.getDestination().getCity(),entity.getDestination().getCountry());
        return location;
    }


}