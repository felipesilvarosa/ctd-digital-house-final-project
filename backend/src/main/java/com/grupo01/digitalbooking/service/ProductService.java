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
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    @Transactional(readOnly = true)
    public List<ProductDTO> getProducts(){
        List<Product> response = repository.findAll();
        if (response.isEmpty())throw new NotFoundException("No product was found");
        return response.stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id){
        Product entity = repository.findById(id).orElseThrow(
                ()-> new NotFoundException("No product with provided id was found"));
        return new ProductDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategory(Long categoryId){
        List<Product> response = repository.findByCategory(categoryId);
        if (response==null||response.isEmpty())throw new NotFoundException("No product with provided category was found");
        return response.stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCity(Long cityId){
        List<Product> response = repository.findByCity(cityId);
        if (response==null||response.isEmpty())throw new NotFoundException("No product with provided city was found");
        return response.stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> searchProducts(Map<String, Object> searchCriteria) {

        StringBuilder query = new StringBuilder("SELECT p from Product p WHERE ");

        if(!(searchCriteria.get("cityId")==null||searchCriteria.get("categoryId")==null||
            searchCriteria.get("startDate")==null||searchCriteria.get("endDate")==null)){
            throw new BadRequestException("No acceptable search criteria");
        }

        if(searchCriteria.containsKey("cityId"))
            query.append("p.city.id = ").append(searchCriteria.get("cityId")).append(" AND");
        if(searchCriteria.containsKey("categoryId"))
            query.append("p.category.id = ").append(searchCriteria.get("categoryId")).append(" AND");
        if(searchCriteria.containsKey("startDate"))
            query.append("p.availableDate >= ").append(searchCriteria.get("startDate")).append(" AND");
        if(searchCriteria.containsKey("cityId"))
            query.append("p.availableDate <= ").append(searchCriteria.get("endDate")).append(" AND");

        query.replace(query.length()-3, query.length(),"");

        List<Product> response = repository.search(query.toString());
        if (response==null||response.isEmpty())throw new NotFoundException("No product with provided criteria was found");
        return response.stream().map(ProductDTO::new).collect(Collectors.toList());

    }

    @Transactional
    public ProductDTO createProduct(ProductDTO dto){
        if(dto.getId()!=null)throw new BadRequestException("Do not send ID to create new product, weird shit might happen");
        return new ProductDTO(repository.save(new Product(dto)));
    }

}
