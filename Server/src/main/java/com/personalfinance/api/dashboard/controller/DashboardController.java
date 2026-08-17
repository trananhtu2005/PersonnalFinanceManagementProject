package com.personalfinance.api.dashboard.controller;

import com.personalfinance.api.dashboard.dto.response.DashboardResponse;
import com.personalfinance.api.dashboard.service.AnalysisService;
import com.personalfinance.api.dashboard.service.DashboardService;
import com.personalfinance.api.dashboard.service.ExcelService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final ExcelService excelService;
    private final AnalysisService analysisService;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        DashboardResponse response = dashboardService.getDashBoard(month, year);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportDashboard(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year)
            throws IOException {
        byte[] file = excelService.exportDashboard(month, year);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=dashboard.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @GetMapping("/ai-analysis")
    public ResponseEntity<String> analyze(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        String response = analysisService.analyze(month, year);

        return ResponseEntity.ok(response);
    }
}
