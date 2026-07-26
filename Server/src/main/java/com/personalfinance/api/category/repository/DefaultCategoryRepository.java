package com.personalfinance.api.category.repository;

import com.personalfinance.api.category.entity.DefaultCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultCategoryRepository extends JpaRepository<DefaultCategory, Integer> {

}
