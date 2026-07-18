package org.example.commercebackoffice.customer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.common.exception.ErrorResponse;
import org.example.commercebackoffice.common.message.SuccessCode;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;
import org.example.commercebackoffice.customer.dto.*;
import org.example.commercebackoffice.customer.service.CustomerService;
import org.example.commercebackoffice.item.dto.response.ItemResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "고객 관리", description = "고객 관리 API")
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(
            summary = "고객 목록 조회",
            description = "검색 조건과 페이징 정보를 이용하여 고객 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "고객 목록 조회 성공")
    })
    @GetMapping("/api/customers")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<Page<GetCustomerListResponse>>> getAll(

            @Parameter(description = "검색 키워드(이름, 이메일, 전화번호)", example = "홍길동")
            @RequestParam(required = false) String keyword,

            @Parameter(description = "페이지 번호", example = "1")
            @RequestParam(defaultValue = "1") int page,

            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "정렬 기준", example = "name")
            @RequestParam(defaultValue = "name") String sortBy,

            @Parameter(description = "정렬 방향", example = "ASC")
            @RequestParam(defaultValue = "ASC") String sortOrder,

            @Parameter(description = "고객 상태", example = "ACTIVE")
            @RequestParam(required = false) CustomerStatus status
    ) {
        Sort sort = sortOrder.equalsIgnoreCase("DESC")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<GetCustomerListResponse> result =
                customerService.getCustomers(keyword, status, pageable);

        return ResponseEntity
                .status(SuccessCode.CUSTOMER_LIST_SELECT_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.CUSTOMER_LIST_SELECT_SUCCESS, result));
    }

    @Operation(
            summary = "고객 상세 조회",
            description = "고객 ID를 이용하여 고객 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "고객 조회 성공"),
            @ApiResponse(responseCode = "404", description = "고객을 찾을 수 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "CUSTOMER_NOT_FOUND",
                                    value = """
                                            {
                                              "status": 404,
                                              "message": "CUSTOMER NOT FOUND",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/api/customers/{customerId}")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<GetCustomerDetailResponse>> getOne(
            @Parameter(description = "고객 ID", example = "1", required = true)
            @PathVariable Long customerId) {

        GetCustomerDetailResponse result = customerService.getOne(customerId);

        return ResponseEntity
                .status(SuccessCode.CUSTOMER_DETAIL_SELECT_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.CUSTOMER_DETAIL_SELECT_SUCCESS, result));
    }

    @Operation(
            summary = "고객 정보 수정",
            description = "고객의 기본 정보를 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "고객 정보 수정 성공"),
            @ApiResponse(responseCode = "404", description = "고객을 찾을 수 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "CUSTOMER_NOT_FOUND",
                                    value = """
                                            {
                                              "status": 404,
                                              "message": "CUSTOMER NOT FOUND",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            )
    })
    @PutMapping("/api/customers/{customerId}")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<PutCustomerResponse>> updateCustomer(
            @Parameter(description = "고객 ID", example = "1", required = true)
            @PathVariable Long customerId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "고객 정보 수정 요청",
                    required = true
            )
            @RequestBody PutCustomerRequest request
    ) {
        PutCustomerResponse result = customerService.updateCustomer(customerId, request);

        return ResponseEntity
                .status(SuccessCode.CUSTOMER_INFO_UPDATE_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.CUSTOMER_INFO_UPDATE_SUCCESS, result));
    }

    @Operation(
            summary = "고객 상태 변경",
            description = "고객의 상태를 변경합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "고객 상태 변경 성공"),
            @ApiResponse(responseCode = "404", description = "고객을 찾을 수 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "CUSTOMER_ALREADY_DELETED",
                                    value = """
                                            {
                                              "status": 400,
                                              "message": "CUSTOMER ALREADY DELETED",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            )
    })
    @PatchMapping("/api/customers/{customerId}")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<PatchCustomerResponse>> updateStatus(
            @Parameter(description = "고객 ID", example = "1", required = true)
            @PathVariable Long customerId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "고객 상태 변경 요청",
                    required = true
            )
            @RequestBody PatchCustomerRequest patchCustomerRequest
    ) {
        PatchCustomerResponse result = customerService.updateStatus(customerId, patchCustomerRequest);

        return ResponseEntity
                .status(SuccessCode.CUSTOMER_STATUS_UPDATE_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.CUSTOMER_STATUS_UPDATE_SUCCESS, result));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "고객 삭제 성공",
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                {
                                  "success": true,
                                  "message": "고객 삭제 성공",
                                  "data": null
                                }
                                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "고객을 찾을 수 없음",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "CUSTOMER_NOT_FOUND",
                                    value = """
                                            {
                                              "status": 404,
                                              "message": "CUSTOMER NOT FOUND",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            )
    })
    @DeleteMapping("/api/customers/{customerId}")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<Void>> delete(
            @Parameter(description = "고객 ID", example = "1", required = true)
            @PathVariable Long customerId) {

        customerService.delete(customerId);

        return ResponseEntity
                .status(SuccessCode.CUSTOMER_DELETE_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.CUSTOMER_DELETE_SUCCESS, null));
    }
}