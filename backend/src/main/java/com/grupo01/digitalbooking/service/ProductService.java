package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.*;
import com.grupo01.digitalbooking.dto.NewProductDTO;
import com.grupo01.digitalbooking.dto.ProductDetailedDTO;
import com.grupo01.digitalbooking.repository.*;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final DestinationRepository destinationRepository;
    private final UtilitiesRepository utilitiesRepository;
    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<ProductDetailedDTO> getProducts(){
        List<Product> response = repository.findAll();
        if (response.isEmpty())throw new NotFoundException("No product was found");
        return response.stream().map(ProductDetailedDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDetailedDTO getProductById(Long id){
        Product entity = repository.findById(id).orElseThrow(
                ()-> new NotFoundException("No product with provided id was found"));
        return new ProductDetailedDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<ProductDetailedDTO> searchProducts(Map<String, Object> searchCriteria) {

        if(searchCriteria.get("locationId")==null&&searchCriteria.get("categoryId")==null&&
                searchCriteria.get("startDate")==null&&searchCriteria.get("endDate")==null){
            throw new BadRequestException("No acceptable search criteria");
        }
        if(searchCriteria.get("startDate")!=null&&searchCriteria.get("endDate")==null)
            throw new BadRequestException("Start date without end date");
        if(searchCriteria.get("endDate")!=null&&searchCriteria.get("startDate")==null)
            throw new BadRequestException("End date without start date");

        String query = "SELECT p from Product p WHERE ";

        if(searchCriteria.get("locationId")!=null)
            query+= "p.location.id = " + searchCriteria.get("locationId") + " AND ";
        if(searchCriteria.get("categoryId")!=null)
            query+= "p.category.id = " + searchCriteria.get("categoryId") + " AND ";
        if(query.endsWith(" AND "))
            query = query.substring(0,query.length()-4);
        if(query.endsWith(" WHERE "))
            query = query.substring(0,query.length()-6);

        List<Product> response = repository.search(query);

        if(searchCriteria.get("endDate")!=null&&searchCriteria.get("startDate")!=null){
            Set<LocalDate> searchDates = new HashSet<>();
            LocalDate searchDate = LocalDate.parse(searchCriteria.get("startDate").toString());
            LocalDate endDate = LocalDate.parse(searchCriteria.get("endDate").toString());
            while (!searchDate.isEqual(endDate.plusDays(1))){
                searchDates.add(searchDate);
                searchDate = searchDate.plusDays(1);
            }

            response = response
                    .stream()
                    .filter(p->!p.getUnavailableDates().containsAll(searchDates))
                    .collect(Collectors.toList());
        }
        if (response.isEmpty())throw new NotFoundException("No product with provided criteria was found");
        return response.stream().map(ProductDetailedDTO::new).collect(Collectors.toList());

    }

    @Transactional
    public ProductDetailedDTO createProduct(NewProductDTO dto){

        if (dto.getCategoryId()==null || dto.getImagesIds()==null){
            throw new BadRequestException("Não pode fazer cadastro sem categorias ou imagens");
        }

        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->
                new BadRequestException("Nenhuma categoria com id informada foi encontrada"));
        Destination destination = destinationRepository.findById(dto.getDestinationId()).orElseThrow(()->
                new BadRequestException("Nenhum destino com id informada foi encontrado"));
        List<Utility> utilities = utilitiesRepository.findAllById(dto.getUtilitiesIds());
        List<Image> images = imageRepository.findAllById(dto.getImagesIds());
        if(utilities.size()<dto.getUtilitiesIds().size())
            throw new BadRequestException("Utilidades não foram encontradas para algumas ids informadas");
        if(images.size()<dto.getImagesIds().size())
            throw new BadRequestException("Imagens não foram encontradas para algumas ids informadas");

        Product response = new Product(dto);
        List<Policy> policies = new ArrayList<>();
        dto.getPolicies().forEach((k,v)-> policies.add(new Policy(k,v)));
        response.setPolicies(policies);
        response.setUtilities(utilities);
        response.setImages(images);
        response.setCategory(category);
        response.setDestination(destination);
        response = repository.save(response);
        return new ProductDetailedDTO(response);
    }

    @SuppressWarnings("all")
    @Transactional
    public ProductDetailedDTO editProduct(NewProductDTO dto){

        Product response = repository.findById(dto.getId()).orElseThrow(()->
                new BadRequestException("Nenhum produto com id informada foi encontrado"));

        if (dto.getCategoryId()==null || dto.getImagesIds()==null){
            throw new BadRequestException("Não pode fazer cadastro sem categorias ou imagens");
        }

        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->
                new BadRequestException("Nenhuma categoria com id informada foi encontrada"));
        Destination destination = destinationRepository.findById(dto.getDestinationId()).orElseThrow(()->
                new BadRequestException("Nenhum destino com id informada foi encontrado"));
        List<Utility> utilities = utilitiesRepository.findAllById(dto.getUtilitiesIds());
        List<Image> images = imageRepository.findAllById(dto.getImagesIds());
        if(utilities.size()<dto.getUtilitiesIds().size())
            throw new BadRequestException("Utilidades não foram encontradas para algumas ids informadas");
        if(images.size()<dto.getImagesIds().size())
            throw new BadRequestException("Imagens não foram encontradas para algumas ids informadas");
        response = new Product(dto);
        List<Policy> policies = new ArrayList<>();
        dto.getPolicies().forEach((k,v)-> policies.add(new Policy(k,v)));
        response.setPolicies(policies);
        response.setUtilities(utilities);
        response.setImages(images);
        response.setCategory(category);
        response.setDestination(destination);
        response = repository.save(response);
        return new ProductDetailedDTO(response);
    }

    public void deleteProduct(Long id){
        repository.deleteById(id);
    }

}
