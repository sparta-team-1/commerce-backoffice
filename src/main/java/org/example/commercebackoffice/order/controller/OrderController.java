package org.example.commercebackoffice.order.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.common.dto.ApiResponse;
import org.example.commercebackoffice.common.message.SuccessCode;
import org.example.commercebackoffice.order.domain.dto.*;
import org.example.commercebackoffice.order.domain.enums.OrderStatus;
import org.example.commercebackoffice.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

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

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailResponseDto>> getOrder(@PathVariable Long orderId) {
        OrderDetailResponseDto response = orderService.getOrder(orderId);

        // SuccessCode.ORDER_DETAIL_SELECT_SUCCESS (HttpStatus.OK, "주문 상세 조회에 성공하였습니다.") 사용
        return ResponseEntity
                .status(SuccessCode.ORDER_DETAIL_SELECT_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ORDER_DETAIL_SELECT_SUCCESS, response));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponseDto>> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderStatusUpdateRequestDto requestDto
    ) {
        OrderResponseDto response = orderService.updateOrderStatus(orderId, requestDto);

        // SuccessCode.ORDER_STATUS_UPDATE_SUCCESS (HttpStatus.OK, "주문 상태가 성공적으로 수정되었습니다.") 사용
        return ResponseEntity
                .status(SuccessCode.ORDER_STATUS_UPDATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ORDER_STATUS_UPDATE_SUCCESS, response));
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<OrderResponseDto>> cancelOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderCancelRequestDto requestDto
    ) {
        OrderResponseDto response = orderService.cancelOrder(orderId, requestDto);

        // SuccessCode.ORDER_CANCEL_SUCCESS (HttpStatus.OK, "주문이 취소되었습니다.") 사용
        return ResponseEntity
                .status(SuccessCode.ORDER_CANCEL_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ORDER_CANCEL_SUCCESS, response));
    }
}