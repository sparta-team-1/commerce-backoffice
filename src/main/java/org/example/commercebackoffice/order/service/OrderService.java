package org.example.commercebackoffice.order.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.admin.domain.Admin;
import org.example.commercebackoffice.admin.repository.AdminRepository;
import org.example.commercebackoffice.customer.domain.Customer;
import org.example.commercebackoffice.customer.repository.CustomerRepository;
import org.example.commercebackoffice.item.domain.Item;
import org.example.commercebackoffice.item.repository.ItemRepository;
import org.example.commercebackoffice.order.domain.Order;
import org.example.commercebackoffice.order.domain.dto.OrderCreateRequestDto;
import org.example.commercebackoffice.order.domain.dto.OrderResponseDto;
import org.example.commercebackoffice.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderCreateRequestDto requestDto, Long loginAdminId) {
        Customer customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("고객을 찾을 수 없습니다."));

        Item item = itemRepository.findById(requestDto.getItemId())
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        // 수정: 로그인한 관리자 ID가 있을 때만 조회 (CS 주문 케이스 분기)
        Admin admin = null;
        if (loginAdminId != null) {
            admin = adminRepository.findById(loginAdminId)
                    .orElseThrow(() -> new RuntimeException("관리자를 찾을 수 없습니다."));
        }

        int quantity = requestDto.getQuantity();

        // TODO: 주석 해제하여 재고 검증 및 차감 로직 연동
        // item.validateOrderable(quantity);
        // item.decreaseStock(quantity);

        Order order = Order.create(customer, item, admin, quantity, item.getPrice() * quantity);
        Order saved = orderRepository.save(order);

        return new OrderResponseDto(saved);
    }
}