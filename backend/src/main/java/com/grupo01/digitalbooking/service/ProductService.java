package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.*;
import com.grupo01.digitalbooking.dto.NewProductDTO;
import com.grupo01.digitalbooking.dto.ProductDetailedDTO;
import com.grupo01.digitalbooking.repository.CategoryRepository;
import com.grupo01.digitalbooking.repository.DestinationRepository;
import com.grupo01.digitalbooking.repository.ProductRepository;
import com.grupo01.digitalbooking.repository.UtilityRepository;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final DestinationRepository destinationRepository;
    private final UtilityRepository utilityRepository;
    private final AwsS3OperationsService s3Service;

    @Transactional(readOnly = true)
    public List<ProductDetailedDTO> getProducts(){
        List<Product> response = repository.findAll();
        if (response.isEmpty())throw new NotFoundException("Produto não encontrado");
        return response.stream().map(ProductDetailedDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDetailedDTO getProductById(Long id){
        Product entity = repository.findById(id).orElseThrow(
                ()-> new NotFoundException("Nenhum produto com esta id foi encontrado"));
        return new ProductDetailedDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<ProductDetailedDTO> searchProducts(Map<String, Object> searchCriteria) {

        if(searchCriteria.get("destinationId")==null&&searchCriteria.get("categoryId")==null&&
                searchCriteria.get("startDate")==null&&searchCriteria.get("endDate")==null){
            throw new BadRequestException("Nenhum critério de busca aceitável");
        }
        if(searchCriteria.get("startDate")!=null&&searchCriteria.get("endDate")==null)
            throw new BadRequestException("Data inicial sem data final");
        if(searchCriteria.get("endDate")!=null&&searchCriteria.get("startDate")==null)
            throw new BadRequestException("Data final sem data inicial");

        String query = "SELECT p from Product p WHERE ";

        if(searchCriteria.get("destinationId")!=null)
            query+= "p.location.id = " + searchCriteria.get("destinationId") + " AND ";
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
        if (response.isEmpty())throw new NotFoundException("Nenhum produto com estes critérios foi encontrado");
        return response.stream().map(ProductDetailedDTO::new).collect(Collectors.toList());

    }

    @Transactional
    public ProductDetailedDTO createProduct(NewProductDTO dto,List<MultipartFile>images){

        if (dto.getCategoryId()==null || dto.getDestinationId()==null||images==null||images.isEmpty()){
            throw new BadRequestException("Não pode fazer cadastro sem categoria, imagens, ou destino");
        }
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->
                new NotFoundException("Nenhuma categoria com id informada foi encontrada"));
        Destination destination = destinationRepository.findById(dto.getDestinationId()).orElseThrow(()->
                new NotFoundException("Nenhum destino com id informada foi encontrado"));
        List<Utility> utilities = utilityRepository.findAllById(dto.getUtilitiesIds());
        if(utilities.size()<dto.getUtilitiesIds().size())
            throw new NotFoundException("Utilidades não foram encontradas para algumas ids informadas");

        Product response = new Product(dto);
        List<Policy> policies = new ArrayList<>();
        dto.getPolicies().forEach((k,v)-> policies.add(new Policy(k,v)));
        response.setPolicies(policies);
        response.setCategory(category);
        response.setDestination(destination);
        response.setUtilities(utilities);
        response = repository.save(response);
        response = repository.findById(response.getId()).get();
        response.getImages().addAll(s3Service.uploadAndRegisterImages(images, response));
        return new ProductDetailedDTO(response);
    }

    @SuppressWarnings("all")
    @Transactional
    public ProductDetailedDTO editProduct(NewProductDTO dto, List<MultipartFile> images){

        Product response = repository.findById(dto.getId()).orElseThrow(()->
                new NotFoundException("Nenhum produto com id informada foi encontrado"));

        if (dto.getCategoryId()==null || dto.getDestinationId()==null||images==null||images.isEmpty()){
            throw new BadRequestException("Não pode fazer cadastro sem categoria, imagens, ou destino");
        }
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->
                new NotFoundException("Nenhuma categoria com id informada foi encontrada"));
        Destination destination = destinationRepository.findById(dto.getDestinationId()).orElseThrow(()->
                new NotFoundException("Nenhum destino com id informada foi encontrado"));
        List<Utility> utilities = utilityRepository.findAllById(dto.getUtilitiesIds());
        if(utilities.size()<dto.getUtilitiesIds().size())
            throw new NotFoundException("Utilidades não foram encontradas para algumas ids informadas");

        response = new Product(dto);
        List<Policy> policies = new ArrayList<>();
        dto.getPolicies().forEach((k,v)-> policies.add(new Policy(k,v)));
        response.setPolicies(policies);
        response.setCategory(category);
        response.setDestination(destination);
        response.setUtilities(utilities);
        response = repository.save(response);
        response.setImages(s3Service.uploadAndRegisterImages(images,response));
        return new ProductDetailedDTO(response);
    }

    public void deleteProduct(Long id){
        repository.deleteById(id);
    }

}
