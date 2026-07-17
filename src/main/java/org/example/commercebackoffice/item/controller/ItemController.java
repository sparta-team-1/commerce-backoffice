package org.example.commercebackoffice.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.common.dto.ApiResponse;
import org.example.commercebackoffice.common.message.SuccessCode;
import org.example.commercebackoffice.item.domain.enums.ItemStatus;
import org.example.commercebackoffice.item.dto.request.ItemCreateRequestDto;
import org.example.commercebackoffice.item.dto.request.ItemUpdateRequestDto;
import org.example.commercebackoffice.item.dto.request.ItemStatusUpdateRequestDto;
import org.example.commercebackoffice.item.dto.request.ItemStockUpdateRequestDto;
import org.example.commercebackoffice.item.dto.response.ItemResponseDto;
import org.example.commercebackoffice.item.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // 상품 등록 (Create)
    @PostMapping
    public ResponseEntity<ApiResponse<ItemResponseDto>> createItem(@Valid @RequestBody ItemCreateRequestDto requestDto) {
        ItemResponseDto response = itemService.createItem(requestDto);

        return ResponseEntity
                .status(SuccessCode.ITEM_CREATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ITEM_CREATE_SUCCESS, response));
    }

    // 상품 단건 조회 (Read)
    @GetMapping("/{itemId}")
    public ResponseEntity<ApiResponse<ItemResponseDto>> getItem(@PathVariable Long itemId) {
        ItemResponseDto response = itemService.getItem(itemId);

        return ResponseEntity
                .status(SuccessCode.ITEM_DETAIL_SELECT_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ITEM_DETAIL_SELECT_SUCCESS, response));
    }

    // 상품 전체조회 (Search & Paging)
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ItemResponseDto>>> getAllItems(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) ItemStatus status,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<ItemResponseDto> response = itemService.getAllItems(keyword, category, status, pageable);

        return ResponseEntity
                .status(SuccessCode.ITEM_PAGING_SELECT_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ITEM_PAGING_SELECT_SUCCESS, response));
    }

    // 상품 기본 정보 전체 수정
    @PutMapping("/{itemId}")
    public ResponseEntity<ApiResponse<ItemResponseDto>> updateItem(
            @PathVariable Long itemId,
            @Valid @RequestBody ItemUpdateRequestDto requestDto) {
        ItemResponseDto response = itemService.updateItem(itemId, requestDto);

        // SuccessCode.ITEM_INFO_UPDATE_SUCCESS 사용 (200 OK)
        return ResponseEntity
                .status(SuccessCode.ITEM_INFO_UPDATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ITEM_INFO_UPDATE_SUCCESS, response));
    }

    // 상품 상태 단독 수정 (상태 변경)
    @PatchMapping("/{itemId}/status")
    public ResponseEntity<ApiResponse<ItemResponseDto>> updateItemStatus(
            @PathVariable Long itemId,
            @Valid @RequestBody ItemStatusUpdateRequestDto requestDto) {
        ItemResponseDto response = itemService.updateItemStatus(itemId, requestDto);

        // SuccessCode.ITEM_INFO_UPDATE_SUCCESS 사용 (200 OK)
        return ResponseEntity
                .status(SuccessCode.ITEM_INFO_UPDATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ITEM_INFO_UPDATE_SUCCESS, response));
    }

    // 상품 재고 단독 수정 (재고 변경)
    @PatchMapping("/{itemId}/stock")
    public ResponseEntity<ApiResponse<ItemResponseDto>> updateItemStock(
            @PathVariable Long itemId,
            @Valid @RequestBody ItemStockUpdateRequestDto requestDto) {
        ItemResponseDto response = itemService.updateItemStock(itemId, requestDto);

        return ResponseEntity
                .status(SuccessCode.ITEM_STOCK_UPDATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ITEM_STOCK_UPDATE_SUCCESS, response));
    }

    // 상품 삭제 (Soft Delete)
    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);

        return ResponseEntity
                .status(SuccessCode.ITEM_DELETE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ITEM_DELETE_SUCCESS, null));
    }
}
