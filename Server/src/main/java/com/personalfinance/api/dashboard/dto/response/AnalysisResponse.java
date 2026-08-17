package com.personalfinance.api.dashboard.dto.response;

import com.personalfinance.api.transaction.dto.response.TransactionResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnalysisResponse {

    private final DashboardResponse dashboard;
    private final List<TransactionResponse> transactions;
}
