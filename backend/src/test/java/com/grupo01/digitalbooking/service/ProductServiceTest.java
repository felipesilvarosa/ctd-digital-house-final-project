package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.*;
import com.grupo01.digitalbooking.dto.NewProductDTO;
import com.grupo01.digitalbooking.dto.PolicyDTO;
import com.grupo01.digitalbooking.dto.ProductDetailedDTO;
import com.grupo01.digitalbooking.repository.*;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private DestinationRepository destinationRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private UtilityRepository utilityRepository;
    @Mock
    private AwsS3OperationsService s3Service;

    private Long existingId;
    private Long nonExistingId;
    private Product responseProduct;
    private NewProductDTO testInput;
    private List<MultipartFile> testImagesInput;
    private List<Long> nonExistingIdList;
    private List<Long> exceedingIdList;
    private Long hotelCategoryId;
    private Long nonHotelCategoryId;

    @BeforeEach
    void init(){
        Policy policy1 = new Policy();
        policy1.setId(1L);
        policy1.setType("A");
        policy1.setPolicyDescriptions(List.of());
        Policy policy2 = new Policy();
        policy2.setId(2L);
        policy2.setType("B");
        policy2.setPolicyDescriptions(List.of());
        Policy policy3 = new Policy();
        policy3.setId(3L);
        policy3.setType("C");
        policy3.setPolicyDescriptions(List.of());
        this.existingId = 5L;
        this.nonExistingId = 0L;
        this.hotelCategoryId = 10L;
        this.nonHotelCategoryId = 11L;
        this.testInput = new NewProductDTO();
        this.testInput.setId(existingId);
        this.testInput.setCategoryId(existingId);
        this.testInput.setUtilitiesIds(List.of());

        this.testInput.setPolicies(Map.of(
                policy1.getType(),new PolicyDTO(policy1),
                policy2.getType(),new PolicyDTO(policy2),
                policy3.getType(),new PolicyDTO(policy3)
        ));
        List<Image> existingImages = new ArrayList<>();
        existingImages.add(new Image(existingId));
        List<Long> existingIdList = List.of(existingId);
        this.responseProduct = new Product();
        this.responseProduct.setId(existingId);
        this.responseProduct.setImages(existingImages);
        this.responseProduct.setDestination(new Destination(existingId));
        this.responseProduct.setCategory(new Category((existingId)));
        this.responseProduct.setReservations(List.of());
        this.responseProduct.setUtilities(List.of());
        this.responseProduct.setPolicies(List.of(policy1,policy2,policy3));
        this.nonExistingIdList = List.of(nonExistingId);
        this.testImagesInput = new ArrayList<>();
        this.testImagesInput.add(new MockMultipartFile("Mock", (byte[]) null));
        List<Product> findAllResponse = List.of(responseProduct);
        Product saveResponse = new Product();
        saveResponse.setId(existingId);
        saveResponse.setCategory(new Category(existingId));
        saveResponse.setDestination(new Destination(existingId));
        saveResponse.setImages(existingImages);
        saveResponse.setReservations(List.of());
        saveResponse.setUtilities(List.of());
        saveResponse.setPolicies(List.of(policy1,policy2,policy3));
        Category hotelCategory = new Category(hotelCategoryId);
        hotelCategory.setTitle("Hotéis");
        Category nonHotelCategory = new Category(nonHotelCategoryId);
        nonHotelCategory.setTitle("Non-Hotel");
        Destination responseDestination = new Destination();
        responseDestination.setLatitude("10.0");
        responseDestination.setLongitude("10.0");
        when(repository.findAll()).thenReturn(findAllResponse);
        when(repository.save(any(Product.class))).thenReturn(saveResponse);
        when(repository.findById(existingId)).thenReturn(Optional.of(responseProduct));
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
        when(repository.search(any(String.class))).thenReturn(List.of());
        when(categoryRepository.findById(hotelCategoryId)).thenReturn(Optional.of(hotelCategory));
        when(categoryRepository.findById(nonHotelCategoryId)).thenReturn(Optional.of(nonHotelCategory));
        when(categoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        when(destinationRepository.findById(existingId)).thenReturn(Optional.of(responseDestination));
        when(destinationRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        when(imageRepository.findAllById(existingIdList)).thenReturn(List.of(new Image()));
        when(imageRepository.findAllById(nonExistingIdList)).thenReturn(List.of());
        when(utilityRepository.findAllById(existingIdList)).thenReturn(List.of(new Utility()));
        when(utilityRepository.findAllById(nonExistingIdList)).thenReturn(List.of());
        when(s3Service.uploadAndRegisterImages(testImagesInput,responseProduct)).thenReturn(List.of());
        doNothing().when(repository).deleteById(any(Long.class));
    }

    @Test
    void getProductsShouldReturnDTOList(){
        List<ProductDetailedDTO> testOutput = service.getProducts();
        assertNotNull(testOutput);
    }

    @Test
    void getProductsShouldThrowExceptionWhenNoProductFound(){
        when(repository.findAll()).thenReturn(List.of());
        assertThrows(NotFoundException.class, ()->service.getProducts());
    }

    @Test
    void getProductByIdShouldReturnDTOList(){
        ProductDetailedDTO testOutput = service.getProductById(existingId);
        assertNotNull(testOutput);
    }

    @Test
    void getProductByIdShouldThrowExceptionWhenNoProductFound(){
        assertThrows(NotFoundException.class, ()->service.getProductById(nonExistingId));
    }

    @Test
    void createProductShouldThrowExceptionWhenInvalidParams(){

        testInput.setCategoryId(null);
        assertThrows(BadRequestException.class,()->service.createProduct(testInput,testImagesInput));
        testInput.setCategoryId(hotelCategoryId);
        //substitute for address validation
        assertThrows(BadRequestException.class,()->service.createProduct(testInput,testImagesInput));
        //undo invalid adress
        testImagesInput = null;
        assertThrows(BadRequestException.class,()->service.createProduct(testInput,testImagesInput));
        testImagesInput = new ArrayList<>();
        testImagesInput.add(new MockMultipartFile("Mock",(byte[]) null));
        assertThrows(BadRequestException.class,()->service.createProduct(testInput,testImagesInput));
        testInput.setCategoryId(nonExistingId);
        assertThrows(NotFoundException.class,()->service.createProduct(testInput,testImagesInput));
        testInput.setCategoryId(nonHotelCategoryId);
        testInput.setStars(5);
        assertThrows(BadRequestException.class, ()->service.createProduct(testInput,testImagesInput));
        testInput.setCategoryId(hotelCategoryId);
        testInput.setStars(null);
        assertThrows(BadRequestException.class, ()->service.createProduct(testInput,testImagesInput));
        testInput.setStars(5);
        //substitute for address validation
        assertThrows(NotFoundException.class,()->service.createProduct(testInput,testImagesInput));
        //undo invalid address
        testInput.setUtilitiesIds(nonExistingIdList);
        assertThrows(NotFoundException.class,()->service.createProduct(testInput,testImagesInput));


    }

    @Test
    void createProductShouldReturnDTOWhenValidIds(){
        testInput.setCategoryId(hotelCategoryId);
        testInput.setStars(5);
        //substitute fora ddress validation
        ProductDetailedDTO testOutput = service.createProduct(testInput,testImagesInput);
        assertNotNull(testOutput);
        testInput.setCategoryId(nonHotelCategoryId);
        testInput.setStars(null);
        testOutput = service.createProduct(testInput,testImagesInput);
        assertNotNull(testOutput);
    }

    @Test
    void deleteProductShouldCallRepositoryDeleteByIdOnce(){
        service.deleteProduct(1L);
        verify(repository,times(1)).deleteById(1L);
    }

    @Test
    void editProductShouldThrowExceptionWhenInvalidParams(){

        testInput.setId(nonExistingId);
        assertThrows(NotFoundException.class,()->service.editProduct(testInput,testImagesInput));
        testInput.setId(existingId);
        testInput.setCategoryId(null);
        assertThrows(BadRequestException.class,()->service.editProduct(testInput,testImagesInput));
        testInput.setCategoryId(hotelCategoryId);
        //substitute for address validation
        assertThrows(BadRequestException.class,()->service.createProduct(testInput,testImagesInput));
        //undo invalid adress
        testImagesInput = null;
        assertThrows(BadRequestException.class,()->service.createProduct(testInput,testImagesInput));
        testImagesInput = new ArrayList<>();
        testImagesInput.add(new MockMultipartFile("Mock",(byte[]) null));
        assertThrows(BadRequestException.class,()->service.createProduct(testInput,testImagesInput));
        testInput.setCategoryId(nonExistingId);
        assertThrows(NotFoundException.class,()->service.createProduct(testInput,testImagesInput));
        testInput.setCategoryId(nonHotelCategoryId);
        testInput.setStars(5);
        assertThrows(BadRequestException.class, ()->service.createProduct(testInput,testImagesInput));
        testInput.setCategoryId(hotelCategoryId);
        testInput.setStars(null);
        assertThrows(BadRequestException.class, ()->service.createProduct(testInput,testImagesInput));
        testInput.setStars(5);
        //substitute for address validation
        assertThrows(NotFoundException.class,()->service.createProduct(testInput,testImagesInput));
        //undo invalid address
        testInput.setUtilitiesIds(nonExistingIdList);
        assertThrows(NotFoundException.class,()->service.editProduct(testInput,testImagesInput));
    }

    @Test
    void editProductShouldReturnDTOWhenValidParams(){
        testInput.setCategoryId(hotelCategoryId);
        testInput.setStars(5);
        ProductDetailedDTO testOutput = service.editProduct(testInput,testImagesInput);
        assertNotNull(testOutput);
        testInput.setCategoryId(nonHotelCategoryId);
        testInput.setStars(null);
        testOutput = service.editProduct(testInput,testImagesInput);
        assertNotNull(testOutput);
    }

    @Test
    void searchProductsShouldThrowExceptionWhenNoCriteria(){
        Map<String,Object> searchCriteria = new HashMap<>();
        searchCriteria.put("destinationId",null);
        searchCriteria.put("categoryId",null);
        searchCriteria.put("startDate",null);
        searchCriteria.put("endDate",null);
        assertThrows(BadRequestException.class,()->service.searchProducts(searchCriteria));
    }

    @Test
    void searchProductsShouldThrowExceptionWhenInvalidParams(){
        Map<String,Object> searchCriteria = new HashMap<>();
        searchCriteria.put("destinationId",null);
        searchCriteria.put("categoryId",null);
        searchCriteria.put("startDate",null);
        searchCriteria.put("endDate",null);
        assertThrows(BadRequestException.class,()->service.searchProducts(searchCriteria));
        searchCriteria.put("startDate", LocalDate.now());
        assertThrows(BadRequestException.class,()->service.searchProducts(searchCriteria));
        searchCriteria.put("startDate",null);
        searchCriteria.put("endDate",LocalDate.now());
        assertThrows(BadRequestException.class,()->service.searchProducts(searchCriteria));
    }

    @Test
    void searchProductShouldThrowExceptionWhenNoProductFound(){
        Map<String,Object> searchCriteria = new HashMap<>();
        searchCriteria.put("destinationId",nonExistingId);
        searchCriteria.put("categoryId",nonExistingId);
        searchCriteria.put("startDate",null);
        searchCriteria.put("endDate",null);
        assertThrows(NotFoundException.class,()->service.searchProducts(searchCriteria));
    }

    @Test
    void searchProductsShouldReturnDTOList(){
        when(repository.search(any(String.class))).thenReturn(List.of(responseProduct));
        Map<String,Object> searchCriteria = new HashMap<>();
        searchCriteria.put("destinationId",existingId);
        searchCriteria.put("categoryId",existingId);
        searchCriteria.put("startDate",null);
        searchCriteria.put("endDate",null);
        List<ProductDetailedDTO> testOutput = service.searchProducts(searchCriteria);
        assertNotNull(testOutput);
        searchCriteria.put("destinationId",null);
        searchCriteria.put("categoryId",null);
        searchCriteria.put("startDate","2022-01-01");
        searchCriteria.put("endDate","2022-12-31");
        testOutput = service.searchProducts(searchCriteria);
        assertNotNull(testOutput);
    }

}