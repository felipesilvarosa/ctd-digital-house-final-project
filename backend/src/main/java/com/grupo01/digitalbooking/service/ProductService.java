package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.*;
import com.grupo01.digitalbooking.dto.ProductDTO;
import com.grupo01.digitalbooking.repository.*;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final CityRepository cityRepository;
    private final CharacteristicRepository characteristicRepository;
    private final ImageRepository imageRepository;

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
    public List<ProductDTO> searchProducts(Map<String, Object> searchCriteria) {

        if(searchCriteria.get("cityId")==null&&searchCriteria.get("categoryId")==null&&
                searchCriteria.get("startDate")==null&&searchCriteria.get("endDate")==null){
            throw new BadRequestException("No acceptable search criteria");
        }

        StringBuilder query = new StringBuilder("SELECT p from Product p WHERE ");

        if(searchCriteria.get("cityId")!=null)
            query.append("p.city.id = ").append(searchCriteria.get("cityId")).append(" AND ");
        if(searchCriteria.get("categoryId")!=null)
            query.append("p.category.id = ").append(searchCriteria.get("categoryId")).append(" AND ");
        if(searchCriteria.get("startDate")!=null)
            query.append("p.availableDate >= ").append(searchCriteria.get("startDate")).append(" AND ");
        if(searchCriteria.get("endDate")!=null)
            query.append("p.availableDate <= ").append(searchCriteria.get("endDate")).append(" AND ");

        query.replace(query.length()-4, query.length(),"");

        List<Product> response = repository.search(query.toString());
        if (response==null||response.isEmpty())throw new NotFoundException("No product with provided criteria was found");
        return response.stream().map(ProductDTO::new).collect(Collectors.toList());

    }

    @Transactional
    public ProductDTO createProduct(ProductDTO dto){

        if (dto.getCategoryId()==null || dto.getImageIds()==null){
            throw new RuntimeException("Não pode fazer cadastro sem categorias ou imagens");
        }

        categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->
                new BadRequestException("No category with provided id was found"));
        cityRepository.findById(dto.getCategoryId()).orElseThrow(()->
                new BadRequestException("No city with provided id was found"));
        imageRepository.findAllById(dto.getImageIds()).stream().findAny().orElseThrow(()->
                new BadRequestException("No image with provided id was found"));

        return new ProductDTO(repository.save(new Product(dto)));
    }

}
