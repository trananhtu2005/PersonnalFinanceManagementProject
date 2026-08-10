package com.personalfinance.api.category.repository;

import com.personalfinance.api.category.entity.CategoryType;
import com.personalfinance.api.category.entity.DefaultCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultCategoryRepository extends JpaRepository<DefaultCategory, Integer> {

    boolean existsByName(String name);

    List<DefaultCategory> findByType(CategoryType type);
}
