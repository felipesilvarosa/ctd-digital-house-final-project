package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.Category;
import com.grupo01.digitalbooking.dto.CategoryDTO;
import com.grupo01.digitalbooking.repository.CategoryRepository;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;


    @Test
    void createShouldCreateNewCategory(){
        CategoryDTO categoryDTO = mockCategoryDTO();
        categoryService.createCategory(categoryDTO);
        verify(categoryRepository, Mockito.times(1)).save(any());

    }

    @Test
    void createShouldFailWhenCategoryAlreadyExists(){
        CategoryDTO categoryDTO = mockCategoryDTO();
        Category category = new Category(categoryDTO);

        when(categoryRepository.findCategoryByTitle(categoryDTO.getTitle())).thenReturn(category);

        BadRequestException e = assertThrows(BadRequestException.class,
                ()-> categoryService.createCategory(categoryDTO));

        assertTrue(e.getMessage().contains("Category " + categoryDTO.getTitle() + " already exists"));
    }

    @Test
    void editShouldEditWhenCategoryAlreadyExists(){
        CategoryDTO categoryDTO = mockCategoryDTO();
        Category category = new Category(categoryDTO);

        when(categoryRepository.findById(categoryDTO.getId())).thenReturn(Optional.of(category));
        categoryService.editCategory(categoryDTO);
        verify(categoryRepository, Mockito.times(1)).save(any());
    }

    @Test
    void editShouldFailWhenCategoryDontExists(){
        CategoryDTO categoryDTO = mockCategoryDTO();

        when(categoryRepository.findById(categoryDTO.getId())).thenReturn(Optional.empty());

        NotFoundException e = assertThrows(NotFoundException.class,
                ()-> categoryService.editCategory(categoryDTO));

        assertTrue(e.getMessage().contains("Category not found"));
    }

    private CategoryDTO mockCategoryDTO(){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setTitle("null");
        categoryDTO.setDescription("null");
        categoryDTO.setImageUrl("null");

        return categoryDTO;
    }
}