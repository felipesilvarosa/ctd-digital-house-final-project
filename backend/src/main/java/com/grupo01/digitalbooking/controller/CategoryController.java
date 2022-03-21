package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.CategoryDTO;
import com.grupo01.digitalbooking.dto.DefaultResponseDTO;
import com.grupo01.digitalbooking.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.grupo01.digitalbooking.dto.DefaultResponseDTO.Status.SUCCESS;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> findAllCategories() {
        List<CategoryDTO> response = categoryService.getCategories();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{title}")
    public ResponseEntity<CategoryDTO> getCategoryByTitle(@PathVariable String title) {
        CategoryDTO response = categoryService.getCategoryByTitle(title);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO response = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryDTO> editCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO response = categoryService.editCategory(categoryDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
