package com.grupo01.digitalbooking.repository;

import com.grupo01.digitalbooking.domain.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitiesRepository extends JpaRepository<Cities,Long> {
    Cities findByName(String name);
}
