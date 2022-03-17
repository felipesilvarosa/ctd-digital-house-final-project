package com.grupo01.digitalbooking.repository;

import com.grupo01.digitalbooking.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM Product p WHERE p.category.title = :category")
    List<Product> findByCategory(@Param("category") String category);

    @Query("SELECT p FROM Product p WHERE p.category.title = :city")
    List<Product> findByCity(@Param("city") String city);

}
