package org.example.commercebackoffice.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.common.dto.ApiResponse;
import org.example.commercebackoffice.common.message.SuccessCode;
import org.example.commercebackoffice.dashboard.controller.dto.response.DashboardDataDto;
import org.example.commercebackoffice.dashboard.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    //대시 보드 정보 조회
    @GetMapping
    public ResponseEntity<?> getDashboardData() {
        DashboardDataDto data = dashboardService.getDashboardData();

        return ResponseEntity.ok(ApiResponse.of(SuccessCode.DASHBOARD_SELECT_SUCCESS, data));
    }
}
