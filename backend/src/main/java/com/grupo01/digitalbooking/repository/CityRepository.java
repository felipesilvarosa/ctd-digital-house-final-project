package com.grupo01.digitalbooking.repository;

import com.grupo01.digitalbooking.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {
    City findByName(String name);
}
