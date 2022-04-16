package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.NewProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.EAGER;
import static org.hibernate.annotations.CascadeType.ALL;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer stars;
    private Integer rating;
    private Double latitude;
    private Double longitude;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @OneToMany(mappedBy = "product")
    private List<Image> images;

    @ManyToMany()
    private List<Utility> utilities;

    @OneToMany(mappedBy = "product")
    @Cascade(ALL)
    private List<Policy> policies;

    @OneToMany(mappedBy = "product")
    private List<Reservation> reservations;

    public Product(NewProductDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.stars = dto.getStars();
        this.rating = dto.getRating();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.category = new Category(dto.getCategoryId());
        this.destination = new Destination(dto.getDestinationId());
        this.utilities = dto.getUtilitiesIds()
                .stream()
                .map(Utility::new)
                .collect(Collectors.toList());

    }

    public Product(Long id) {
        this.id = id;
    }

    public Set<LocalDate> getUnavailableDates() {
        if(getReservations()==null)return null;
        Set<LocalDate> unavailableDates = new TreeSet<>();
        getReservations().forEach(reservation -> {
            LocalDate endDate = reservation.getCheckoutDateTime().toLocalDate();
            LocalDate unavailableDate = reservation.getCheckinDateTime().toLocalDate();
            while (!unavailableDate.isEqual(endDate.plusDays(1))) {
                unavailableDates.add(unavailableDate);
                unavailableDate = unavailableDate.plusDays(1);
            }
        });
        return unavailableDates;
    }
}
