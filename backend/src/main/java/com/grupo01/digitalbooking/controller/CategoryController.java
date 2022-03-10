package com.grupo01.digitalbooking.controller;

import com.grupo01.digitalbooking.dto.CategoryDTO;
import com.grupo01.digitalbooking.dto.DefaultResponseDTO;
import com.grupo01.digitalbooking.service.CategoryService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<DefaultResponseDTO> findAllCategories() {
        List<CategoryDTO> response = categoryService.getCategories();
        Map<String, List<CategoryDTO>> data = Map.of("category", response);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS, data, "Categories retrieved successfully"));
    }

    @GetMapping("/{title}")
    public ResponseEntity<DefaultResponseDTO> getCategoryByTitle(@PathVariable String title) {
        CategoryDTO response = categoryService.getCategoryByTitle(title);
        Map<String, CategoryDTO> data = Map.of("category", response);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS, data, "Category retrieved successfully"));
    }

    @PostMapping("/new")
    public ResponseEntity<DefaultResponseDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO response = categoryService.createCategory(categoryDTO);
        Map<String, CategoryDTO> data = Map.of("category", response);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS, data, "Category created successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DefaultResponseDTO> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS, "Category deleted successfully"));
    }

    @PutMapping("/edit")
    public ResponseEntity<DefaultResponseDTO> editCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO response = categoryService.editCategory(categoryDTO);
        Map<String, CategoryDTO> data = Map.of("category", response);
        return ResponseEntity.ok(new DefaultResponseDTO(SUCCESS, data, "Category edited successfully"));
    }
}
