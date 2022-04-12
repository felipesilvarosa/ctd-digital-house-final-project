package com.grupo01.digitalbooking.domain;

import com.grupo01.digitalbooking.dto.NewProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "product")
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @ManyToMany
    private List<Utilities> utilities;

    @OneToMany(mappedBy = "product")
    private List<Policy> policies;

    @OneToMany(mappedBy = "product")
    private List<Reservation> reservations;

    public Product(NewProductDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.category = new Category(dto.getCategoryId());
        this.destination = new Destination(dto.getCityId());
        this.description = dto.getDescription();
        this.reservations = dto.getReservationsIds()
                .stream()
                .map(Reservation::new)
                .collect(Collectors.toList());
        this.utilities = dto.getCharacteristicIds()
                .stream()
                .map(Utilities::new)
                .collect(Collectors.toList());
    }

    public Set<LocalDate> getUnavailableDates() {
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
