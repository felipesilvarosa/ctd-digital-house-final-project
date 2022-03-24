package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.ProductDTO;
import com.grupo01.digitalbooking.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@Api(value ="", tags = {"Produtos"})
@Tag(name ="Produtos", description="End point para controle de produtos")
public class ProductController {

    private final ProductService service;

    @ApiOperation("Busca por todos os podutos")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(){
        List<ProductDTO> response = service.getProducts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("Busca um produto po ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductsById(@PathVariable Long id){
        ProductDTO response = service.getProductById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("Faz uma busca por produto de acordo com os parametros")
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam(required = false) Long cityId,
                                                             @RequestParam(required = false) Long categoryId,
                                                             @RequestParam(required = false) String startDate,
                                                             @RequestParam(required = false) String endDate){
        Map<String, Object> params = new HashMap<>();
        params.put("cityId",cityId);
        params.put("categoryId",categoryId);
        params.put("startDate",startDate);
        params.put("endDate",endDate);
        List<ProductDTO> response = service.searchProducts(params);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("Cria um novo produto")
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO dto){
        ProductDTO response = service.createProduct(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
