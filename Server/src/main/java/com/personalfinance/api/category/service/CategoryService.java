package com.personalfinance.api.category.service;

import com.personalfinance.api.category.dto.request.CreateCategoryRequest;
import com.personalfinance.api.category.dto.request.UpdateCategoryRequest;
import com.personalfinance.api.category.dto.response.CategoryResponse;
import com.personalfinance.api.category.entity.CategoryType;
import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAllCategories(CategoryType type);

    void createCategory(CreateCategoryRequest request);

    void updateCategory(Integer id, UpdateCategoryRequest request);

    void deleteCategory(Integer id);
}
