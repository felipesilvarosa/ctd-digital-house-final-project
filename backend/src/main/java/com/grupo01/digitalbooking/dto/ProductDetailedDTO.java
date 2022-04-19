package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Image;
import com.grupo01.digitalbooking.domain.Product;
import com.grupo01.digitalbooking.domain.Utility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
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
    private String address;
    private Integer stars;
    private Integer rating;
    private String latitude;
    private String longitude;
    private List<String> images;
    private Set<LocalDate> unavailable;
    private List<String> utilities;
    private Map<String, Map<String,Object>> policies;


    public ProductDetailedDTO(Product entity){

        this.id = entity.getId();
        this.title = entity.getName();
        this.description = entity.getDescription();
        this.address = entity.getAddress();
        this.category = entity.getCategory().getTitle();
        this.stars = entity.getStars();
        this.rating = entity.getRating();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.unavailable = entity.getUnavailableDates();
        this.policies = formatPoliciesJSON(entity);
        this.destination = entity.getDestination().getCity()+", "+
                entity.getDestination().getCountry();
        this.images = entity.getImages()
                .stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());
        this.utilities = entity.getUtilities()
                .stream()
                .map(Utility::getName)
                .collect(Collectors.toList());
    }

    private Map<String, Map<String,Object>> formatPoliciesJSON(Product entity) {
        Map<String,Map<String,Object>> formatedJson = new HashMap<>();
        List<Map<String,String>> rulesDescriptions = entity
                .getPolicies()
                .stream()
                .filter(policy -> policy.getType().equals("rules"))
                .map(policy -> Map.of(
                        "icon",policy.getIcon(),
                        "description",policy.getDescription()))
                .collect(Collectors.toList());
        List<Map<String,String>> safetyDescriptions = entity
                .getPolicies()
                .stream()
                .filter(policy -> policy.getType().equals("safety"))
                .map(policy -> Map.of(
                        "icon",policy.getIcon(),
                        "description",policy.getDescription()))
                .collect(Collectors.toList());
        List<Map<String,String>> cancelingDescriptions = entity
                .getPolicies()
                .stream()
                .filter(policy -> policy.getType().equals("canceling"))
                .map(policy -> Map.of(
                        "icon",policy.getIcon(),
                        "description",policy.getDescription()))
                .collect(Collectors.toList());

        formatedJson.put("rules",Map.of(
                "title","Regras da casa",
                "order",0,
                "descriptions",rulesDescriptions));
        formatedJson.put("safety",Map.of(
                "title","Saúde e segurança",
                "order",1,
                "descriptions",safetyDescriptions));
        formatedJson.put("canceling",Map.of(
                "title","Políticas de cancelamento",
                "order",2,
                "descriptions",cancelingDescriptions));

        return formatedJson;

    }


}