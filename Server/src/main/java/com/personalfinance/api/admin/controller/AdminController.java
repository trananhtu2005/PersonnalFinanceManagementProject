package com.personalfinance.api.admin.controller;

import com.personalfinance.api.admin.dto.response.AdminDashboardResponse;
import com.personalfinance.api.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> getDashboard() {
        AdminDashboardResponse response = adminService.getDashboard();

        return ResponseEntity.ok(response);
    }
}
