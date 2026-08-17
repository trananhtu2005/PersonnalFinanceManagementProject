package com.personalfinance.api.dashboard.service;

import com.personalfinance.api.dashboard.dto.response.AnalysisResponse;

public interface AiService {

    String analyze(AnalysisResponse response);
}
