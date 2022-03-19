package com.grupo01.digitalbooking.repository;

import com.grupo01.digitalbooking.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;


public class CustomProductRepositoryImpl implements CustomProductRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Product> search(String query) {
        return entityManager.createQuery(query,Product.class).getResultList();
    }
}
