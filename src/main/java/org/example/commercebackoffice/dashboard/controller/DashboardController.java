package org.example.commercebackoffice.dashboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.common.exception.ErrorResponse;
import org.example.commercebackoffice.common.message.SuccessCode;
import org.example.commercebackoffice.dashboard.controller.dto.response.DashboardDataDto;
import org.example.commercebackoffice.dashboard.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "대시보드", description = "대시보드 API")
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(
            summary = "대시보드 정보 조회",
            description = "대시보드에 필요한 통계 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "대시보드 정보 조회 성공"),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "서버 오류",
                                    value = """
                                            {
                                              "status": 500,
                                              "message": "INTERNAL_SERVER_ERROR",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<DashboardDataDto>> getDashboardData() {

        DashboardDataDto data = dashboardService.getDashboardData();

        return ResponseEntity.ok(org.example.commercebackoffice.common.dto.ApiResponse
                .of(SuccessCode.DASHBOARD_SELECT_SUCCESS, data));
    }
}