package com.grupo01.digitalbooking.repository;

import com.grupo01.digitalbooking.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByTitle(@NotEmpty String title);
}
