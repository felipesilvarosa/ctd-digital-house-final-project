package com.grupo01.digitalbooking.repository;

import com.grupo01.digitalbooking.domain.Product;

import java.util.List;

public interface CustomProductRepository{

    List<Product> search(String query);

}
