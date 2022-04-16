package com.grupo01.digitalbooking.repository;

import com.grupo01.digitalbooking.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByTitle(String title);

}
