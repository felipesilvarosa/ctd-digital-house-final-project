package com.grupo01.digitalbooking.service;


import com.grupo01.digitalbooking.domain.Category;
import com.grupo01.digitalbooking.dto.CategoryDTO;
import com.grupo01.digitalbooking.repository.CategoryRepository;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import com.grupo01.digitalbooking.service.exceptions.ConflictException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(
                category -> new CategoryDTO(category,category.getProducts().size())
        ).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {

        if (id==null) throw new BadRequestException("A id não pode ser nula.");

        Category category = categoryRepository.findById(id).orElseThrow(()->
                new NotFoundException("Categoria não encontrada"));

        return new CategoryDTO(category,category.getProducts().size());

    }

    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO);
        Category categoryFound = (categoryRepository.findCategoryByTitle(categoryDTO.getTitle().toLowerCase()));

        if(Objects.nonNull(categoryFound)){
            throw new ConflictException("Categoria " + categoryDTO.getTitle() + " já cadastrada");
        }

        categoryRepository.save(category);
        return new CategoryDTO(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if(id==null) throw new BadRequestException("Id inválida");
        categoryRepository.deleteById(id);
    }

    @Transactional
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
