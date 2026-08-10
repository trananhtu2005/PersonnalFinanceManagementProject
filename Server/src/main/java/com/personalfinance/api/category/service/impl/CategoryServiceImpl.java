package com.personalfinance.api.category.service.impl;

import com.personalfinance.api.category.dto.request.CreateCategoryRequest;
import com.personalfinance.api.category.dto.request.UpdateCategoryRequest;
import com.personalfinance.api.category.dto.response.CategoryResponse;
import com.personalfinance.api.category.entity.Category;
import com.personalfinance.api.category.entity.CategoryType;
import com.personalfinance.api.category.entity.Color;
import com.personalfinance.api.category.repository.CategoryRepository;
import com.personalfinance.api.category.repository.ColorRepository;
import com.personalfinance.api.category.service.CategoryService;
import com.personalfinance.api.user.entity.User;
import com.personalfinance.api.user.service.CurrentUserService;
import com.personalfinance.exception.AppException;
import com.personalfinance.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final CurrentUserService currentUserService;

    @Override
    public List<CategoryResponse> getAllCategories(CategoryType type) {
        User user = currentUserService.getCurrentUser();
        List<Category> categories;

        if (type == null) {
            categories = categoryRepository.findByUser(user);
        } else {
            categories = categoryRepository.findByUserAndType(user, type);
        }

        return categories.stream().map(c
                -> CategoryResponse.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .colorCode(c.getColor().getCode())
                        .type(c.getType())
                        .build()
        ).toList();
    }

    @Override
    public void createCategory(CreateCategoryRequest request) {
        User user = currentUserService.getCurrentUser();

        if (categoryRepository.existsByUserAndName(user, request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }

        Color color = colorRepository.findById(request.getColorId())
                .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));

        Category category = Category.builder()
                .name(request.getName())
                .color(color)
                .type(request.getType())
                .user(user)
                .build();
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Integer id, UpdateCategoryRequest request) {
        if (request.isEmpty()) {
            throw new AppException(ErrorCode.NO_DATA_TO_UPDATE);
        }

        User user = currentUserService.getCurrentUser();
        Category category = categoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        if (request.getName() != null) {
            if (categoryRepository.existsByUserAndName(user, request.getName())) {
                throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
            }

            category.setName(request.getName());
        }
        if (request.getColorId() != null) {
            Color color = colorRepository.findById(request.getColorId())
                    .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));
            category.setColor(color);
        }
        if (request.getType() != null) {
            category.setType(request.getType());
        }

        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        User user = currentUserService.getCurrentUser();
        Category category = categoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
    }
}
