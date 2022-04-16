package com.grupo01.digitalbooking.repository;

import com.grupo01.digitalbooking.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
