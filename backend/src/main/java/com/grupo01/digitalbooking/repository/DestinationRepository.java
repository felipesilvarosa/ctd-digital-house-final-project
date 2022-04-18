package com.grupo01.digitalbooking.repository;

import com.grupo01.digitalbooking.domain.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DestinationRepository extends JpaRepository<Destination,Long> {
    @Query("SELECT d from Destination d where lower(d.city)=lower(:city) and lower(d.country)=lower(:country)")
    Optional<Destination> findByCityAndCountry(@Param("city") String city, @Param("country") String country);
}
