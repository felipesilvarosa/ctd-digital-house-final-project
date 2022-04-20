package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.*;
import com.grupo01.digitalbooking.dto.NewProductDTO;
import com.grupo01.digitalbooking.dto.ProductDetailedDTO;
import com.grupo01.digitalbooking.repository.*;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.math.RoundingMode.FLOOR;
import static org.springframework.web.reactive.function.client.WebClient.builder;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final DestinationRepository destinationRepository;
    private final UtilityRepository utilityRepository;
    private final AwsS3OperationsService s3Service;
    private final PolicyRepository policyRepository;

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
            return getProducts();
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

            response = response.stream()
                    .filter(p-> p.getUnavailableDates()
                    .stream().noneMatch(searchDates::contains))
                    .collect(Collectors.toList());
        }
        if (response.isEmpty())throw new NotFoundException("Nenhum produto com estes critérios foi encontrado");
        return response.stream().map(ProductDetailedDTO::new).collect(Collectors.toList());

    }

    @Transactional
    public ProductDetailedDTO createProduct(NewProductDTO dto,List<MultipartFile>images){

        if (dto.getCategoryId()==null || dto.getAddress()==null||images==null||images.isEmpty())
            throw new BadRequestException("Não pode fazer cadastro sem categoria, imagens, ou endereço");

        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->
            new NotFoundException("Nenhuma categoria com id informada foi encontrada"));

//        if((!category.getTitle().equals("Hotéis")&&dto.getStars()!=null)||
//                (category.getTitle().equals("Hotéis")&&dto.getStars()==null))
//            throw new BadRequestException("Somente hotéis podem e devem possuir estrelas");

        String[] addressDetails = dto.getAddress().split(",");
        Destination destination = destinationRepository.findByCityAndCountry(addressDetails[addressDetails.length-3].trim(),
                addressDetails[addressDetails.length-1].trim()).orElseThrow(
                ()-> new NotFoundException("Nenhum destino foi encontrado com base no endereço informado"));

        List<Utility> utilities = utilityRepository.findAllByNameIgnoreCase(dto.getUtilitiesNames());
        if(utilities.size()<dto.getUtilitiesNames().size())
            throw new NotFoundException("Utilidades não foram encontradas para algumas ids informadas");

        String[] coordinates = getCoordinatesFromApi(dto.getAddress());
        Product response = new Product(dto);
        response.setLatitude(coordinates[0]);
        response.setLongitude(coordinates[1]);
        List<Policy> policies = policyRepository.findAllById(dto.getPoliciesIds());
        policies.forEach(p->p.getProducts().add(response));
        response.setPolicies(policies);
        response.setCategory(category);
        response.setDestination(destination);
        utilities.forEach(u -> u.getProducts().add(response));
        response.setUtilities(utilities);
        response.setId(repository.save(response).getId());
        response.setImages(s3Service.uploadAndRegisterImages(images, response));
        return new ProductDetailedDTO(response);
    }

    @Transactional
    public ProductDetailedDTO editProduct(NewProductDTO dto, List<MultipartFile> images){

        repository.findById(dto.getId()).orElseThrow(()->
            new NotFoundException("Nenhum produto com id informada foi encontrado"));

        if (dto.getCategoryId()==null || dto.getAddress()==null||images==null||images.isEmpty())
            throw new BadRequestException("Não pode fazer cadastro sem categoria, imagens, ou endereço");

        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->
                new NotFoundException("Nenhuma categoria com id informada foi encontrada"));

//        if((!category.getTitle().equals("Hotéis")&&dto.getStars()!=null)||
//                (category.getTitle().equals("Hotéis")&&dto.getStars()==null))
//            throw new BadRequestException("Somente hotéis podem e devem possuir estrelas");

        String[] addressDetails = dto.getAddress().split(",");
        Destination destination = destinationRepository.findByCityAndCountry(addressDetails[addressDetails.length-3].trim(),
                addressDetails[addressDetails.length-1].trim()).orElseThrow(
                ()-> new NotFoundException("Nenhum destino foi encontrado com base no endereço informado"));

        List<Utility> utilities = utilityRepository.findAllByNameIgnoreCase(dto.getUtilitiesNames());
        if(utilities.size()<dto.getUtilitiesNames().size())
            throw new NotFoundException("Utilidades não foram encontradas para algumas ids informadas");

        String[] coordinates = getCoordinatesFromApi(dto.getAddress());
        Product response = new Product(dto);
        List<Policy> policies = policyRepository.findAllById(dto.getPoliciesIds());
        policies.forEach(p->p.getProducts().add(response));
        response.setLongitude(coordinates[0]);
        response.setLatitude(coordinates[1]);
        response.setPolicies(policies);
        response.setCategory(category);
        response.setDestination(destination);
        utilities.forEach(u->u.getProducts().add(response));
        response.setUtilities(utilities);
        response.setId(repository.save(response).getId());
        response.getImages().addAll(s3Service.uploadAndRegisterImages(images,response));
        return new ProductDetailedDTO(response);
    }

    public void deleteProduct(Long id){
        try{
            log.info("does this this product id exist? {}",repository.findById(id).isPresent());
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            log.info("Tried to delete product with id {} but it wasn't found",id);
        }
    }

    private String[] getCoordinatesFromApi(String address) {

        WebClient client = builder()
                .baseUrl("https://nominatim.openstreetmap.org")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        Map<String,Object> response = Objects.requireNonNull(client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("q", address)
                        .queryParam("format", "json")
                        .queryParam("limit", 1)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {
                }).block()).get(0);

        DecimalFormat df = new DecimalFormat("#.#######");
        df.setRoundingMode(FLOOR);
        return new String[]{
                df.format(Double.valueOf((String)response.get("lat"))),
                df.format(Double.valueOf((String)response.get("lon")))
        };
    }
}