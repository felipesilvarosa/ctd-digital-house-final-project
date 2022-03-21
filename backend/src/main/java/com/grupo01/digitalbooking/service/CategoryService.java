package com.grupo01.digitalbooking.service;


import com.grupo01.digitalbooking.domain.Category;
import com.grupo01.digitalbooking.dto.CategoryDTO;
import com.grupo01.digitalbooking.repository.CategoryRepository;
import com.grupo01.digitalbooking.service.exceptions.ConflictException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final String ID_CANNOT_BE_NULL;

    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {

        if (Objects.isNull(id)){
            throw new IllegalArgumentException(ID_CANNOT_BE_NULL);
        }

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {
            return new CategoryDTO(category.get());
        }

        throw new NotFoundException("Categoria não encontrada");

    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO);
        Category categoryFound = (categoryRepository.findCategoryByTitle(categoryDTO.getTitle().toLowerCase()));

        if(Objects.nonNull(categoryFound)){
            throw new ConflictException("Categoria " + categoryDTO.getTitle() + " já cadastrada");
        }

        categoryRepository.save(category);
        return new CategoryDTO(category);
    }

    public void deleteCategory(Long id) {
        if (Objects.isNull(id)){
            throw new IllegalArgumentException(ID_CANNOT_BE_NULL);
        }
        categoryRepository.deleteById(id);
    }


    public CategoryDTO editCategory(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO);
        Optional<Category> categoryFound = categoryRepository.findById(category.getId());
        if(categoryFound.isPresent()) {
            categoryRepository.save(category);
            return new CategoryDTO(category);
        } else {
            throw new NotFoundException("Category not found");
        }
    }
}
