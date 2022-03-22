package com.grupo01.digitalbooking.repository;

import com.grupo01.digitalbooking.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final EntityManager entityManager;

    @Override
    public List<Product> search(String query) {
        return entityManager.createQuery(query,Product.class).getResultList();
    }
}
