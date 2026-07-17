package org.example.commercebackoffice.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.common.dto.ApiResponse;
import org.example.commercebackoffice.common.message.SuccessCode;
import org.example.commercebackoffice.common.exception.ErrorResponse;
import org.example.commercebackoffice.order.domain.dto.*;
import org.example.commercebackoffice.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "주문 관리", description = "주문 관리 API")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = "주문 생성",
            description = "새로운 주문을 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "주문 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(
            @Valid @RequestBody OrderCreateRequestDto requestDto,
            HttpSession session
    ) {
        Long loginAdminId = (Long) session.getAttribute("adminId");
        OrderResponseDto response = orderService.createOrder(requestDto, loginAdminId);

        // SuccessCode.ORDER_CREATE_SUCCESS (HttpStatus.CREATED, "주문이 완료되었습니다.") 사용
        return ResponseEntity
                .status(SuccessCode.ORDER_CREATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ORDER_CREATE_SUCCESS, response));
    }

    @Operation(
            summary = "주문 목록 조회",
            description = "검색 조건과 페이징 정보를 이용하여 주문 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 목록 조회 성공")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponseDto<OrderListResponseDto>>> getOrders(
            @ModelAttribute OrderSearchCondition condition
    ) {
        PageResponseDto<OrderListResponseDto> response = orderService.getOrders(condition);

        // SuccessCode.ORDER_LIST_SELECT_SUCCESS (HttpStatus.OK, "주문 목록 조회에 성공하였습니다.") 사용
        return ResponseEntity
                .status(SuccessCode.ORDER_LIST_SELECT_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ORDER_LIST_SELECT_SUCCESS, response));
    }

    @Operation(
            summary = "주문 상세 조회",
            description = "주문 ID를 이용하여 주문 상세 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 조회 성공"),
            @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailResponseDto>> getOrder(
            @Parameter(description = "주문 ID", example = "1", required = true)
            @PathVariable Long orderId) {

        OrderDetailResponseDto response = orderService.getOrder(orderId);

        // SuccessCode.ORDER_DETAIL_SELECT_SUCCESS (HttpStatus.OK, "주문 상세 조회에 성공하였습니다.") 사용
        return ResponseEntity
                .status(SuccessCode.ORDER_DETAIL_SELECT_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ORDER_DETAIL_SELECT_SUCCESS, response));
    }

    @Operation(
            summary = "주문 상태 변경",
            description = "주문의 상태를 변경합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 상태 변경 성공"),
            @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponseDto>> updateOrderStatus(
            @Parameter(description = "주문 ID", example = "1", required = true)
            @PathVariable Long orderId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "주문 상태 변경 요청",
                    required = true
            )
            @Valid @RequestBody OrderStatusUpdateRequestDto requestDto
    ) {
        OrderResponseDto response = orderService.updateOrderStatus(orderId, requestDto);

        // SuccessCode.ORDER_STATUS_UPDATE_SUCCESS (HttpStatus.OK, "주문 상태가 성공적으로 수정되었습니다.") 사용
        return ResponseEntity
                .status(SuccessCode.ORDER_STATUS_UPDATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ORDER_STATUS_UPDATE_SUCCESS, response));
    }

    @Operation(
            summary = "주문 취소",
            description = "주문을 취소하고 취소 사유를 저장합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 취소 성공"),
            @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<OrderResponseDto>> cancelOrder(
            @Parameter(description = "주문 ID", example = "1", required = true)
            @PathVariable Long orderId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "주문 취소 요청",
                    required = true
            )
            @Valid @RequestBody OrderCancelRequestDto requestDto
    ) {
        OrderResponseDto response = orderService.cancelOrder(orderId, requestDto);

        // SuccessCode.ORDER_CANCEL_SUCCESS (HttpStatus.OK, "주문이 취소되었습니다.") 사용
        return ResponseEntity
                .status(SuccessCode.ORDER_CANCEL_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ORDER_CANCEL_SUCCESS, response));
    }
}