package org.example.commercebackoffice.item.service;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.common.exception.CustomException;
import org.example.commercebackoffice.common.exception.ErrorCode;
import org.example.commercebackoffice.review.domain.dto.ReviewStatsDto;
import org.example.commercebackoffice.review.domain.dto.ReviewSummaryDto;
import org.example.commercebackoffice.review.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.example.commercebackoffice.admin.domain.Admin;
import org.example.commercebackoffice.admin.repository.AdminRepository;
import org.example.commercebackoffice.item.domain.Item;
import org.example.commercebackoffice.item.domain.enums.ItemStatus;
import org.example.commercebackoffice.item.dto.request.ItemCreateRequestDto;
import org.example.commercebackoffice.item.dto.request.ItemUpdateRequestDto;
import org.example.commercebackoffice.item.dto.request.ItemStatusUpdateRequestDto;
import org.example.commercebackoffice.item.dto.request.ItemStockUpdateRequestDto;
import org.example.commercebackoffice.item.dto.response.ItemResponseDto;
import org.example.commercebackoffice.item.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final AdminRepository adminRepository;
    private final ReviewService reviewService;

    @Transactional
    public ItemResponseDto createItem(ItemCreateRequestDto requestDto) {
        Admin admin = adminRepository.findById(requestDto.getAdminId())
                .orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));

        Item item = new Item(admin, requestDto.getName(), requestDto.getCategory(),
                requestDto.getPrice(), requestDto.getStock(), requestDto.getStatus());
        return new ItemResponseDto(itemRepository.save(item));
    }
    
    @Transactional(readOnly = true)
    public ItemResponseDto getItem(Long itemId) {
        Item item = itemRepository.findByIdAndStatusNot(itemId, ItemStatus.DISCONTINUED)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        ReviewStatsDto reviewStats = reviewService.getReviewStats(itemId);
        List<ReviewSummaryDto> latestReviews = reviewService.getLatestReviews(itemId);

        return new ItemResponseDto(item, reviewStats, latestReviews);
    }

    @Transactional(readOnly = true)
    public Page<ItemResponseDto> getAllItems(String keyword, String category, ItemStatus status, Pageable pageable) {
        return itemRepository.searchItems(keyword, category, status, pageable)
                .map(ItemResponseDto::new);
    }

    @Transactional
    public ItemResponseDto updateItem(Long itemId, ItemUpdateRequestDto requestDto) {
        Item item = itemRepository.findByIdAndStatusNot(itemId, ItemStatus.DISCONTINUED)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND_OR_UNMODIFIABLE));

        item.updateItem(requestDto.getName(), requestDto.getCategory(), requestDto.getPrice());
        return new ItemResponseDto(item);
    }

    // 1. 상태 전용 부분 수정
    @Transactional
    public ItemResponseDto updateItemStatus(Long itemId, ItemStatusUpdateRequestDto requestDto) {
        Item item = itemRepository.findByIdAndStatusNot(itemId, ItemStatus.DISCONTINUED)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND_OR_UNMODIFIABLE));

        item.updateStatus(requestDto.getStatus());
        return new ItemResponseDto(item);
    }

    // 2. 재고 전용 부분 수정
    @Transactional
    public ItemResponseDto updateItemStock(Long itemId, ItemStockUpdateRequestDto requestDto) {
        Item item = itemRepository.findByIdAndStatusNot(itemId, ItemStatus.DISCONTINUED)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND_OR_UNMODIFIABLE));

        item.updateStock(requestDto.getStock());
        return new ItemResponseDto(item);
    }

    @Transactional
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
        item.discontinue();
    }

}