package org.example.commercebackoffice.order.service;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.admin.domain.Admin;
import org.example.commercebackoffice.admin.repository.AdminRepository;
import org.example.commercebackoffice.customer.domain.Customer;
import org.example.commercebackoffice.customer.domain.enums.CustomerStatus;
import org.example.commercebackoffice.customer.repository.CustomerRepository;
import org.example.commercebackoffice.item.domain.Item;
import org.example.commercebackoffice.item.repository.ItemRepository;
import org.example.commercebackoffice.order.domain.Order;
import org.example.commercebackoffice.order.domain.dto.*;
import org.example.commercebackoffice.order.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.commercebackoffice.common.exception.CustomException;
import org.example.commercebackoffice.common.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;
    private final AdminRepository adminRepository;
    
    //주문 생성 로직
    @Transactional
    public OrderResponseDto createOrder(OrderCreateRequestDto requestDto, Long loginAdminId) {
        Customer customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));

        if (customer.getStatus() == CustomerStatus.INACTIVE) {
            throw new CustomException(ErrorCode. CUSTOMER_NOT_FOUND);
        }

        Item item = itemRepository.findById(requestDto.getItemId())
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        // 수정: 로그인한 관리자 ID가 있을 때만 조회
        Admin admin = null;
        if (loginAdminId != null) {
            admin = adminRepository.findById(loginAdminId)
                    .orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));
        }

        int quantity = requestDto.getQuantity();

        // 주문시 상품 재고 차감
        item.validateOrderable(quantity);
        item.decreaseStock(quantity);

        Order order = Order.create(customer, item, admin, quantity, item.getPrice() * quantity);
        Order saved = orderRepository.save(order);

        return new OrderResponseDto(saved);
    }
    
    // 주문 리스트 조회
    @Transactional(readOnly = true) 
    public PageResponseDto<OrderListResponseDto> getOrders(OrderSearchCondition condition) {

        Sort.Direction direction = "asc".equalsIgnoreCase(condition.getOrder())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(
                condition.getPage() - 1,
                condition.getSize(),
                Sort.by(direction, condition.getSort())
        );

        Page<Order> orders = orderRepository.search(
                condition.getStatus(),
                condition.getKeyword(),
                pageable
        );
        return new PageResponseDto<>(orders.map(OrderListResponseDto::new));
    }

    // 주문 상세 조회
    @Transactional
    public OrderDetailResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        return new OrderDetailResponseDto(order);
    }

    // 주문 상태 수정
    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatusUpdateRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        order.updateStatus(requestDto.getStatus());

        return new OrderResponseDto(order);
    }

    // 주문 취소
    @Transactional
    public OrderResponseDto cancelOrder(Long orderId, OrderCancelRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        order.cancel(requestDto.getCancelReason());

        // 재고 복구 로직
        order.getItem().increaseStock(order.getQuantity());

        return new OrderResponseDto(order);
    }
    
}