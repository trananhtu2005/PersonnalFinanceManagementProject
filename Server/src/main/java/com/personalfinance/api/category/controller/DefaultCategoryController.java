package com.personalfinance.api.category.controller;

import com.personalfinance.api.category.dto.request.CreateDefaultCategoryRequest;
import com.personalfinance.api.category.dto.request.UpdateDefaultCategoryRequest;
import com.personalfinance.api.category.dto.response.DefaultCategoryResponse;
import com.personalfinance.api.category.entity.CategoryType;
import com.personalfinance.api.category.service.DefaultCategoryService;
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
@RequestMapping("/default-categories")
@RequiredArgsConstructor
public class DefaultCategoryController {

    private final DefaultCategoryService defaultCategoryService;

    @GetMapping
    public ResponseEntity<List<DefaultCategoryResponse>> getAllDefaultCategories(@RequestParam(required = false) CategoryType type) {
        List<DefaultCategoryResponse> response = defaultCategoryService.getAllDefaultCategories(type);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createDefaultCategory(@Valid @RequestBody CreateDefaultCategoryRequest request) {
        defaultCategoryService.createDefaultCategory(request);
        MessageResponse response = MessageResponse.builder()
                .message("Category has been created successfully!")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> updateDefaultCategory(@PathVariable("id") Integer id, @RequestBody UpdateDefaultCategoryRequest request) {
        defaultCategoryService.updateDefaultCategory(id, request);
        MessageResponse response = MessageResponse.builder()
                .message("Category has been updated successfully!")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteDefaultCategory(@PathVariable("id") Integer id) {
        defaultCategoryService.deleteDefaultCategory(id);
        MessageResponse response = MessageResponse.builder()
                .message("Category has been deleted successfully!")
                .build();

        return ResponseEntity.ok(response);
    }
}
