package com.grupo01.digitalbooking.service;


import com.grupo01.digitalbooking.domain.Category;
import com.grupo01.digitalbooking.dto.CategoryDTO;
import com.grupo01.digitalbooking.repository.CategoryRepository;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import com.grupo01.digitalbooking.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryDTO::new).toList();
    }

    public CategoryDTO getCategoryByTitle(String title) {
        Category category = categoryRepository.findCategoryByTitle(title);
        return new CategoryDTO(category);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO);
        Category categoryFound = (categoryRepository.findCategoryByTitle(categoryDTO.getTitle().toLowerCase()));

        if(categoryFound != null){
            throw new BadRequestException("Category " + categoryDTO.getTitle() + " already exists");
        }

        categoryRepository.save(category);
        return new CategoryDTO(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }


    /**
     * TODO: Verificar se necessita regras de negocios para a edição
     *
     * */
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
