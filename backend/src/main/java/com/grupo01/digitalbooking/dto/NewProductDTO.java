package com.grupo01.digitalbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewProductDTO {

    private Long id;
    private String name;
    private String description;
    private Integer stars;
    private Integer rating;
    private Double latitude;
    private Double longitude;
    private Long categoryId;
    private Long destinationId;
    private List<Long> utilitiesIds;
    private List<Long> imagesIds;
    private Map<String,PolicyDTO> policies;

}
