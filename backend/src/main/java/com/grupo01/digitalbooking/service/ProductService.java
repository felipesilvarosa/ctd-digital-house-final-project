package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.Product;
import com.grupo01.digitalbooking.dto.ProductDTO;
import com.grupo01.digitalbooking.repository.ProductRepository;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    @Transactional(readOnly = true)
    public List<ProductDTO> getProducts(){
        List<Product> response = repository.findAll();
        if (response.isEmpty())throw new NotFoundException("No product was found");
        return response.stream().map(ProductDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id){
        Product entity = repository.findById(id).orElseThrow(
                ()-> new NotFoundException("No product with provided id was found"));
        return new ProductDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategory(String category){
        List<Product> response = repository.findByCity(category);
        if (response==null||response.isEmpty())throw new NotFoundException("No product with provided category was found");
        return response.stream().map(ProductDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCity(String city){
        List<Product> response = repository.findByCity(city);
        if (response==null||response.isEmpty())throw new NotFoundException("No product with provided city was found");
        return response.stream().map(ProductDTO::new).toList();
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO dto){
        if(dto.getId()!=null)throw new BadRequestException("Do not send ID to create new product, weird shit might happen");
        return new ProductDTO(repository.save(new Product(dto)));
    }

}
