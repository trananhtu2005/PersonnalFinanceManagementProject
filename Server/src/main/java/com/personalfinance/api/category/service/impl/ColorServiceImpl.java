package com.personalfinance.api.category.service.impl;

import com.personalfinance.api.category.dto.request.CreateColorRequest;
import com.personalfinance.api.category.dto.request.UpdateColorRequest;
import com.personalfinance.api.category.dto.response.ColorResponse;
import com.personalfinance.api.category.entity.Color;
import com.personalfinance.api.category.repository.ColorRepository;
import com.personalfinance.api.category.service.ColorService;
import com.personalfinance.exception.AppException;
import com.personalfinance.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    @Override
    public List<ColorResponse> getAllColors() {
        List<Color> colors = colorRepository.findAll();

        return colors.stream().map(c
                -> ColorResponse.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .code(c.getCode())
                        .build()
        ).toList();
    }

    @Override
    public void createColor(CreateColorRequest request) {
        if (colorRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.COLOR_NAME_ALREADY_EXISTS);
        }
        if (colorRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.COLOR_CODE_ALREADY_EXISTS);
        }

        Color color = Color.builder()
                .name(request.getName())
                .code(request.getCode())
                .build();
        colorRepository.save(color);
    }

    @Override
    public void updateColor(Integer id, UpdateColorRequest request) {
        if (request.isEmpty()) {
            throw new AppException(ErrorCode.NO_DATA_TO_UPDATE);
        }

        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));

        if (request.getName() != null) {
            if (colorRepository.existsByName(request.getName())) {
                throw new AppException(ErrorCode.COLOR_NAME_ALREADY_EXISTS);
            }

            color.setName(request.getName());
        }
        if (request.getCode() != null) {
            if (colorRepository.existsByCode(request.getCode())) {
                throw new AppException(ErrorCode.COLOR_CODE_ALREADY_EXISTS);
            }

            color.setCode(request.getCode());
        }

        colorRepository.save(color);
    }

    @Override
    public void deleteColor(Integer id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));
        colorRepository.delete(color);
    }
}
