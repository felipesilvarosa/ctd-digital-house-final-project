package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.DefaultResponseDTO;
import com.grupo01.digitalbooking.dto.ProductDTO;
import com.grupo01.digitalbooking.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.grupo01.digitalbooking.dto.DefaultResponseDTO.Status.SUCCESS;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<DefaultResponseDTO> getProducts(){
        List<ProductDTO> result = service.getProducts();
        Map<String,List<ProductDTO>> data = Map.of("products",result);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS,data,"Products retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponseDTO> getProductsById(@PathVariable Long id){
        ProductDTO result = service.getProductById(id);
        Map<String,ProductDTO> data = Map.of("product",result);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS,data,"Product retrieved successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<DefaultResponseDTO> searchProducts(@RequestParam(required = false) Long cityId,
                                                             @RequestParam(required = false) Long categoryId,
                                                             @RequestParam(required = false) String startDate,
                                                             @RequestParam(required = false) String endDate){
        Map<String, Object> params = new HashMap<>();
        params.put("cityId",cityId);
        params.put("categoryId",categoryId);
        params.put("startDate",startDate);
        params.put("endDate",endDate);
        List<ProductDTO> result = service.searchProducts(params);
        Map<String,List<ProductDTO>> data = Map.of("products",result);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS,data,"Products retrieved successfully"));
    }

    @PostMapping
    public ResponseEntity<DefaultResponseDTO> createProduct(@RequestBody ProductDTO dto){
        ProductDTO result = service.createProduct(dto);
        Map<String, ProductDTO> data = Map.of("product",result);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS,data,"Product created successfully"));
    }
}
