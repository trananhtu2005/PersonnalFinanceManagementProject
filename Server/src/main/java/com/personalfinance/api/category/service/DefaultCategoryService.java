package com.personalfinance.api.category.service;

import com.personalfinance.api.category.dto.request.CreateDefaultCategoryRequest;
import com.personalfinance.api.category.dto.request.UpdateDefaultCategoryRequest;
import com.personalfinance.api.category.dto.response.DefaultCategoryResponse;
import com.personalfinance.api.category.entity.CategoryType;
import java.util.List;

public interface DefaultCategoryService {

    List<DefaultCategoryResponse> getAllDefaultCategories(CategoryType type);

    void createDefaultCategory(CreateDefaultCategoryRequest request);

    void updateDefaultCategory(Integer id, UpdateDefaultCategoryRequest request);

    void deleteDefaultCategory(Integer id);
}
