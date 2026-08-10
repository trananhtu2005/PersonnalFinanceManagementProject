package com.personalfinance.api.category.service.impl;

import com.personalfinance.api.category.dto.request.CreateDefaultCategoryRequest;
import com.personalfinance.api.category.dto.request.UpdateDefaultCategoryRequest;
import com.personalfinance.api.category.dto.response.DefaultCategoryResponse;
import com.personalfinance.api.category.entity.CategoryType;
import com.personalfinance.api.category.entity.DefaultCategory;
import com.personalfinance.api.category.repository.DefaultCategoryRepository;
import com.personalfinance.api.category.service.DefaultCategoryService;
import com.personalfinance.exception.AppException;
import com.personalfinance.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCategoryServiceImpl implements DefaultCategoryService {

    private final DefaultCategoryRepository defaultCategoryRepository;

    @Override
    public List<DefaultCategoryResponse> getAllDefaultCategories(CategoryType type) {
        List<DefaultCategory> defaultCategories;

        if (type == null) {
            defaultCategories = defaultCategoryRepository.findAll();
        } else {
            defaultCategories = defaultCategoryRepository.findByType(type);
        }

        return defaultCategories.stream().map(dc
                -> DefaultCategoryResponse.builder()
                        .id(dc.getId())
                        .name(dc.getName())
                        .type(dc.getType())
                        .build()
        ).toList();
    }

    @Override
    public void createDefaultCategory(CreateDefaultCategoryRequest request) {
        if (defaultCategoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }

        DefaultCategory defaultCategory = DefaultCategory.builder()
                .name(request.getName())
                .type(request.getType())
                .build();
        defaultCategoryRepository.save(defaultCategory);
    }

    @Override
    public void updateDefaultCategory(Integer id, UpdateDefaultCategoryRequest request) {
        if (request.isEmpty()) {
            throw new AppException(ErrorCode.NO_DATA_TO_UPDATE);
        }

        DefaultCategory defaultCategory = defaultCategoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        if (request.getName() != null) {
            if (defaultCategoryRepository.existsByName(request.getName())) {
                throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
            }

            defaultCategory.setName(request.getName());
        }
        if (request.getType() != null) {
            defaultCategory.setType(request.getType());
        }

        defaultCategoryRepository.save(defaultCategory);
    }

    @Override
    public void deleteDefaultCategory(Integer id) {
        DefaultCategory defaultCategory = defaultCategoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        defaultCategoryRepository.delete(defaultCategory);
    }
}
