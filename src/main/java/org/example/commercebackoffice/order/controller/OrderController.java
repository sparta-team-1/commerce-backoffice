package org.example.commercebackoffice.order.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.order.domain.dto.OrderCreateRequestDto;
import org.example.commercebackoffice.order.domain.dto.OrderResponseDto;
import org.example.commercebackoffice.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @Valid @RequestBody OrderCreateRequestDto requestDto,
            HttpSession session
    ) {
        Long loginAdminId = (Long) session.getAttribute("adminId");
        OrderResponseDto response = orderService.createOrder(requestDto, loginAdminId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}