package com.grupo01.digitalbooking.repository;

import com.grupo01.digitalbooking.domain.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination,Long> {
    Destination findByName(String name);
}
