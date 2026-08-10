package com.personalfinance.api.saving_goal.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSavingGoalRequest {

    @NotBlank(message = "Title is required!")
    private String title;

    private String description;

    @NotBlank(message = "Target is required!")
    @DecimalMin(value = "0.01", message = "Target must be greater than 0!")
    private BigDecimal target;

    @NotBlank(message = "End date is required!")
    @Future(message = "End date must be in the future!")
    private LocalDate endAt;
}
