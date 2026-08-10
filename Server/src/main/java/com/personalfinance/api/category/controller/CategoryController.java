package com.personalfinance.api.category.controller;

import com.personalfinance.api.category.dto.request.CreateCategoryRequest;
import com.personalfinance.api.category.dto.request.UpdateCategoryRequest;
import com.personalfinance.api.category.dto.response.CategoryResponse;
import com.personalfinance.api.category.entity.CategoryType;
import com.personalfinance.api.category.service.CategoryService;
import com.personalfinance.common.MessageResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(@RequestParam(required = false) CategoryType type) {
        List<CategoryResponse> response = categoryService.getAllCategories(type);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        categoryService.createCategory(request);
        MessageResponse response = MessageResponse
                .builder()
                .message("Category has been created successfully!")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> updateCategory(@PathVariable("id") Integer id, @RequestBody UpdateCategoryRequest request) {
        categoryService.updateCategory(id, request);
        MessageResponse response = MessageResponse
                .builder()
                .message("Category has been updated successfully!")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCategory(@PathVariable("id") Integer id) {
        categoryService.deleteCategory(id);
        MessageResponse response = MessageResponse
                .builder()
                .message("Category has been deleted successfully!")
                .build();

        return ResponseEntity.ok(response);
    }
}
