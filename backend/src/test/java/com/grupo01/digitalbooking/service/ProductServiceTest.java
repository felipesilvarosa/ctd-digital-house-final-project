package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.Category;
import com.grupo01.digitalbooking.domain.City;
import com.grupo01.digitalbooking.domain.Image;
import com.grupo01.digitalbooking.domain.Product;
import com.grupo01.digitalbooking.dto.ProductDTO;
import com.grupo01.digitalbooking.repository.CategoryRepository;
import com.grupo01.digitalbooking.repository.CityRepository;
import com.grupo01.digitalbooking.repository.ImageRepository;
import com.grupo01.digitalbooking.repository.ProductRepository;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService service;
    @Mock
    private ProductRepository repository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private ImageRepository imageRepository;

    private Long existingId;
    private Long nonExistingId;
    private Product responseProduct;
    private ProductDTO testInput;
    private List<Long> existingImageIds;
    private List<Long> nonExistingImageIds;

    @BeforeEach
    void init(){
        this.existingId = 5L;
        this.nonExistingId = 0L;
        this.testInput = new ProductDTO();
        this.testInput.setId(existingId);
        this.testInput.setCategoryId(existingId);
        this.testInput.setCharacteristicIds(List.of());
        this.testInput.setImageIds(existingImageIds);
        this.testInput.setAvailableDates(List.of());
        this.existingImageIds = List.of(existingId);
        this.responseProduct = new Product();
        this.responseProduct.setId(existingId);
        this.responseProduct.setImages(List.of(new Image(existingId)));
        this.responseProduct.setCity(new City(existingId));
        this.responseProduct.setCategory(new Category((existingId)));
        this.responseProduct.setAvailableDates(List.of());
        this.responseProduct.setCharacteristics(List.of());
        this.nonExistingImageIds = List.of();
        List<Product> findAllResponse = List.of(responseProduct);
        Product saveResponse = new Product();
        saveResponse.setId(existingId);
        saveResponse.setCategory(new Category(existingId));
        saveResponse.setCity(new City(existingId));
        saveResponse.setImages(List.of(new Image(existingId)));
        saveResponse.setAvailableDates(List.of());
        saveResponse.setCharacteristics(List.of());
        when(repository.findAll()).thenReturn(findAllResponse);
        when(repository.save(any(Product.class))).thenReturn(saveResponse);
        when(repository.findById(existingId)).thenReturn(Optional.of(responseProduct));
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
        when(repository.search(any(String.class))).thenReturn(List.of());
        when(categoryRepository.findById(existingId)).thenReturn(Optional.of(new Category()));
        when(categoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        when(cityRepository.findById(existingId)).thenReturn(Optional.of(new City()));
        when(cityRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        when(imageRepository.findAllById(existingImageIds)).thenReturn(List.of(new Image()));
        when(imageRepository.findAllById(nonExistingImageIds)).thenReturn(List.of());
        doNothing().when(repository).deleteById(any(Long.class));
    }

    @Test
    void getProductsShouldReturnDTOList(){
        List<ProductDTO> testOutput = service.getProducts();
        assertNotNull(testOutput);
    }
    @Test
    void getProductsShouldThrowExceptionWhenNoProductFound(){
        when(repository.findAll()).thenReturn(List.of());
        assertThrows(NotFoundException.class, ()->service.getProducts());
    }
    @Test
    void getProductByIdShouldReturnDTOList(){
        ProductDTO testOutput = service.getProductById(existingId);
        assertNotNull(testOutput);
    }
    @Test
    void getProductByIdShouldThrowExceptionWhenNoProductFound(){
        assertThrows(NotFoundException.class, ()->service.getProductById(nonExistingId));
    }
    @Test
    void createProductShouldThrowExceptionWhenInvalidIds(){
        testInput.setCategoryId(null);
        assertThrows(BadRequestException.class,()->service.createProduct(testInput));
        testInput.setCategoryId(existingId);
        testInput.setImageIds(null);
        assertThrows(BadRequestException.class,()->service.createProduct(testInput));
        testInput.setImageIds(existingImageIds);
        testInput.setCategoryId(nonExistingId);
        assertThrows(BadRequestException.class,()->service.createProduct(testInput));
        testInput.setCategoryId(existingId);
        testInput.setCityId(nonExistingId);
        assertThrows(BadRequestException.class,()->service.createProduct(testInput));
        testInput.setCityId(existingId);
        testInput.setImageIds(nonExistingImageIds);
        assertThrows(BadRequestException.class,()->service.createProduct(testInput));

    }
    @Test
    void createProductShouldReturnDTOWhenValidIds(){
        testInput.setImageIds(existingImageIds);
        testInput.setCategoryId(existingId);
        testInput.setCityId(existingId);
        ProductDTO testOutput = service.createProduct(testInput);
        assertEquals(5L,testOutput.getId());
    }
    @Test
    void deleteProductShouldCallRepositoryDeleteByIdOnce(){
        service.deleteProduct(1L);
        verify(repository,times(1)).deleteById(1L);
    }

    @Test
    void editProductShouldThrowExceptionWhenIdDoesNotExist(){
        testInput.setId(nonExistingId);
        assertThrows(NotFoundException.class,()->service.editProduct(testInput));
    }
    @Test
    void editProductShouldReturnDTOWhenIdExists(){
        ProductDTO testOutput = service.editProduct(testInput);
        assertEquals(5L,testOutput.getId());
    }

    @Test
    void searchProductsShouldThrowExceptionWhenNoCriteria(){
        Map<String,Object> searchCriteria = new HashMap<>();
        searchCriteria.put("cityId",null);
        searchCriteria.put("categoryId",null);
        searchCriteria.put("startDate",null);
        searchCriteria.put("endDate",null);
        assertThrows(BadRequestException.class,()->service.searchProducts(searchCriteria));
    }
    @Test
    void searchProductsShouldThrowExceptionWhenNoProductIsFound(){
        Map<String,Object> searchCriteria = new HashMap<>();
        searchCriteria.put("cityId",existingId);
        searchCriteria.put("categoryId",existingId);
        searchCriteria.put("startDate","");
        searchCriteria.put("endDate","");
        assertThrows(NotFoundException.class,()->service.searchProducts(searchCriteria));
    }
    @Test
    void searchProductsShouldReturnDTOList(){
        when(repository.search(any(String.class))).thenReturn(List.of(responseProduct));
        Map<String,Object> searchCriteria = new HashMap<>();
        searchCriteria.put("cityId",existingId);
        searchCriteria.put("categoryId",existingId);
        searchCriteria.put("startDate","");
        searchCriteria.put("endDate","");
        List<ProductDTO> testOutput = service.searchProducts(searchCriteria);
        assertNotNull(testOutput);

    }


}