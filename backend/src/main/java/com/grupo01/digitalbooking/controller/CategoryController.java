package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.CategoryDTO;
import com.grupo01.digitalbooking.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
@Api(value ="", tags = {"Categorias"})
@Tag(name ="Categorias", description="End point para controle de categorias")
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation("Busca todas as categorias cadastradas")
    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> findAllCategories() {
        List<CategoryDTO> response = categoryService.getCategories();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("Busca uma categoria por id")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO response = categoryService.getCategoryById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("Cria uma nova categoria")
    @PostMapping("/new")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO response = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiOperation("Deleta uma categoria pela id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ApiOperation("Edita uma categoria")
    @PutMapping("/edit")
    public ResponseEntity<CategoryDTO> editCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO response = categoryService.editCategory(categoryDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
