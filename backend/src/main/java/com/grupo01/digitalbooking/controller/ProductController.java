package com.grupo01.digitalbooking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo01.digitalbooking.dto.DefaultResponseDTO;
import com.grupo01.digitalbooking.dto.NewProductDTO;
import com.grupo01.digitalbooking.dto.ProductDetailedDTO;
import com.grupo01.digitalbooking.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.grupo01.digitalbooking.dto.DefaultResponseDTO.Status.SUCCESS;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@Api(tags = {"Produtos"})
@Tag(name ="Produtos", description="End point para controle de produtos")
public class ProductController {

    private final ProductService service;

    @ApiOperation("Busca por todos os produtos")
    @GetMapping
    public ResponseEntity<List<ProductDetailedDTO>> getProducts(){
        List<ProductDetailedDTO> response = service.getProducts();
        return new ResponseEntity<>(response, OK);
    }

    @ApiOperation("Busca um produto po ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailedDTO> getProductsById(@PathVariable Long id){
        ProductDetailedDTO response = service.getProductById(id);
        return new ResponseEntity<>(response, OK);
    }

    @ApiOperation("Faz uma busca por produto de acordo com os parâmetros")
    @GetMapping("/search")
    public ResponseEntity<List<ProductDetailedDTO>> searchProducts(@RequestParam(required = false) Long destinationId,
                                                              @RequestParam(required = false) Long categoryId,
                                                              @RequestParam(required = false) String startDate,
                                                              @RequestParam(required = false) String endDate){
        Map<String, Object> params = new HashMap<>();
        params.put("destinationId",destinationId);
        params.put("categoryId",categoryId);
        params.put("startDate",startDate);
        params.put("endDate",endDate);
        List<ProductDetailedDTO> response = service.searchProducts(params);
        return new ResponseEntity<>(response, OK);
    }

    @ApiOperation("Cria um novo produto")
    @PostMapping
    public ResponseEntity<ProductDetailedDTO> createProduct(String dtoJSON,List<MultipartFile> images) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        NewProductDTO dto = mapper.readValue(dtoJSON,NewProductDTO.class);
        ProductDetailedDTO response = service.createProduct(dto,images);
        return new ResponseEntity<>(response, CREATED);
    }

    @ApiOperation("Atualiza um produto")
    @PutMapping
    public ResponseEntity<ProductDetailedDTO> editProduct(String dtoJSON,List<MultipartFile>images) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        NewProductDTO dto = mapper.readValue(dtoJSON,NewProductDTO.class);
        ProductDetailedDTO response = service.editProduct(dto,images);
        return new ResponseEntity<>(response, OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DefaultResponseDTO> deleteProduct(@RequestParam List<Long> ids){
        ids.forEach(service::deleteProduct);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS,Map.of(),"Produtos deletados"));
    }

}
