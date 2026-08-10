package com.personalfinance.api.category.service;

import com.personalfinance.api.category.dto.request.CreateColorRequest;
import com.personalfinance.api.category.dto.request.UpdateColorRequest;
import com.personalfinance.api.category.dto.response.ColorResponse;
import java.util.List;

public interface ColorService {

    List<ColorResponse> getAllColors();

    void createColor(CreateColorRequest request);

    void updateColor(Integer id, UpdateColorRequest request);

    void deleteColor(Integer id);
}
