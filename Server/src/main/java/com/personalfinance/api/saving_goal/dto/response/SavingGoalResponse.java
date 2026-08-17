package com.personalfinance.api.saving_goal.dto.response;

import com.personalfinance.api.saving_goal.entity.SavingStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SavingGoalResponse {

    private final Integer id;
    private final String title;
    private final String description;
    private final BigDecimal target;
    private final BigDecimal currentAmount;
    private final LocalDate startAt;
    private final LocalDate endAt;
    private final SavingStatus status;
    private final String categoryName;
}
