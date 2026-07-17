package org.example.commercebackoffice.item.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.common.dto.ApiResponse;
import org.example.commercebackoffice.common.message.SuccessCode;
import org.example.commercebackoffice.common.exception.ErrorResponse;
import org.example.commercebackoffice.item.domain.enums.ItemStatus;
import org.example.commercebackoffice.item.dto.request.ItemCreateRequestDto;
import org.example.commercebackoffice.item.dto.request.ItemStatusUpdateRequestDto;
import org.example.commercebackoffice.item.dto.request.ItemStockUpdateRequestDto;
import org.example.commercebackoffice.item.dto.request.ItemUpdateRequestDto;
import org.example.commercebackoffice.item.dto.response.ItemResponseDto;
import org.example.commercebackoffice.item.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품 관리", description = "상품 관리 API")
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @Operation(
            summary = "상품 등록",
            description = "새로운 상품을 등록합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "상품 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<ItemResponseDto>> createItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 등록 요청",
                    required = true
            )
            @Valid @RequestBody ItemCreateRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemService.createItem(requestDto));
    }

    @Operation(
            summary = "상품 상세 조회",
            description = "상품 ID를 이용하여 상품 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 조회 성공"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{itemId}")
    public ResponseEntity<ApiResponse<ItemResponseDto>> getItem(
            @Parameter(description = "상품 ID", example = "1", required = true)
            @PathVariable Long itemId) {

        return ResponseEntity.ok(itemService.getItem(itemId));
    }

    @Operation(
            summary = "상품 목록 조회",
            description = "검색 조건과 페이징 정보를 이용하여 상품 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ItemResponseDto>>> getAllItems(
            @Parameter(description = "검색 키워드", example = "마우스")
            @RequestParam(required = false) String keyword,

            @Parameter(description = "카테고리", example = "전자기기")
            @RequestParam(required = false) String category,

            @Parameter(description = "상품 상태", example = "ON_SALE")
            @RequestParam(required = false) ItemStatus status,

            @Parameter(hidden = true)
            @PageableDefault(size = 10, page = 0)
            Pageable pageable) {
        Page<ItemResponseDto> response = itemService.getAllItems(keyword, category, status, pageable);

        return ResponseEntity
                .status(SuccessCode.ITEM_PAGING_SELECT_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ITEM_PAGING_SELECT_SUCCESS, response));
    }

    @Operation(
            summary = "상품 정보 수정",
            description = "상품의 기본 정보를 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 수정 성공"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PutMapping("/{itemId}")
    public ResponseEntity<ApiResponse<ItemResponseDto>> updateItem(
            @Parameter(description = "상품 ID", example = "1", required = true)
            @PathVariable Long itemId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 정보 수정 요청",
                    required = true
            )
            @Valid @RequestBody ItemUpdateRequestDto requestDto) {
        ItemResponseDto response = itemService.updateItem(itemId, requestDto);

        // SuccessCode.ITEM_INFO_UPDATE_SUCCESS 사용 (200 OK)
        return ResponseEntity
                .status(SuccessCode.ITEM_INFO_UPDATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ITEM_INFO_UPDATE_SUCCESS, response));
    }

    @Operation(
            summary = "상품 상태 변경",
            description = "상품의 판매 상태를 변경합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 상태 변경 성공"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/{itemId}/status")
    public ResponseEntity<ApiResponse<ItemResponseDto>> updateItemStatus(
            @Parameter(description = "상품 ID", example = "1", required = true)
            @PathVariable Long itemId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 상태 변경 요청",
                    required = true
            )
            @Valid @RequestBody ItemStatusUpdateRequestDto requestDto) {
        ItemResponseDto response = itemService.updateItemStatus(itemId, requestDto);

        // SuccessCode.ITEM_INFO_UPDATE_SUCCESS 사용 (200 OK)
        return ResponseEntity
                .status(SuccessCode.ITEM_INFO_UPDATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ITEM_INFO_UPDATE_SUCCESS, response));
    }

    @Operation(
            summary = "상품 재고 변경",
            description = "상품의 재고 수량을 변경합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 재고 변경 성공"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/{itemId}/stock")
    public ResponseEntity<ApiResponse<ItemResponseDto>> updateItemStock(
            @Parameter(description = "상품 ID", example = "1", required = true)
            @PathVariable Long itemId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "상품 재고 변경 요청",
                    required = true
            )
            @Valid @RequestBody ItemStockUpdateRequestDto requestDto) {
        ItemResponseDto response = itemService.updateItemStock(itemId, requestDto);

        return ResponseEntity
                .status(SuccessCode.ITEM_STOCK_UPDATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ITEM_STOCK_UPDATE_SUCCESS, response));
    }

    @Operation(
            summary = "상품 삭제",
            description = "상품을 삭제(Soft Delete)합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "상품 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse<Void>> deleteItem(
            @Parameter(description = "상품 ID", example = "1", required = true)
            @PathVariable Long itemId) {

        itemService.deleteItem(itemId);

        return ResponseEntity
                .status(SuccessCode.ITEM_DELETE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ITEM_DELETE_SUCCESS, null));
    }
}