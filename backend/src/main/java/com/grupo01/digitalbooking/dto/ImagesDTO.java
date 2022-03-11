package com.grupo01.digitalbooking.dto;

import com.grupo01.digitalbooking.domain.Images;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImagesDTO {
    private Long id;
    private String title;
    private String url;

    private ImagesDTO(Images entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.url = entity.getUrl();
    }
}
