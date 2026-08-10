package com.personalfinance.api.category.controller;

import com.personalfinance.api.category.dto.request.CreateColorRequest;
import com.personalfinance.api.category.dto.request.UpdateColorRequest;
import com.personalfinance.api.category.dto.response.ColorResponse;
import com.personalfinance.api.category.service.ColorService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/colors")
@RequiredArgsConstructor
public class ColorController {

    private final ColorService colorService;

    @GetMapping
    public ResponseEntity<List<ColorResponse>> getAllColors() {
        List<ColorResponse> response = colorService.getAllColors();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createColor(@Valid @RequestBody CreateColorRequest request) {
        colorService.createColor(request);
        MessageResponse response = MessageResponse.builder()
                .message("Color has been created successfully!")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> updateColor(@PathVariable("id") Integer id, @RequestBody UpdateColorRequest request) {
        colorService.updateColor(id, request);
        MessageResponse response = MessageResponse.builder()
                .message("Color has been updated successfully!")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteColor(@PathVariable("id") Integer id) {
        colorService.deleteColor(id);
        MessageResponse response = MessageResponse.builder()
                .message("Color has been deleted successfully!")
                .build();

        return ResponseEntity.ok(response);
    }
}
