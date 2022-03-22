package com.grupo01.digitalbooking.repository;

import com.grupo01.digitalbooking.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long>,CustomProductRepository {

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.category.id = :cityId")
    List<Product> findByCity(@Param("cityId") Long cityId);

}
