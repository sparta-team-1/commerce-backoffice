package org.example.commercebackoffice.admin.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.admin.controller.auth.dto.request.LoginRequest;
import org.example.commercebackoffice.admin.controller.auth.dto.request.SignupRequest;
import org.example.commercebackoffice.admin.service.AdminService;
import org.example.commercebackoffice.common.dto.ApiResponse;
import org.example.commercebackoffice.common.message.SuccessCode;
import org.example.commercebackoffice.common.exception.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증", description = "관리자 인증 및 로그인 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AdminService adminService;
    private final static int SESSION_EXPIRATION = 3600;

    @Operation(
            summary = "관리자 회원가입",
            description = "관리자 계정을 등록합니다. 등록 후 SUPER 관리자의 승인이 필요합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 신청 성공",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/register")
    public ResponseEntity<String> signup(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "관리자 회원가입 요청",
                    required = true
            )
            @Valid @RequestBody SignupRequest request
    ) {
        adminService.signup(request);

        return ResponseEntity
                .status(SuccessCode.ADMIN_SIGNUP_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.ADMIN_SIGNUP_SUCCESS, null));
    }

    //테스트용 슈퍼 관리자 추가
    @PostMapping("/register/super")
    public ResponseEntity<?> superAdminSignup(@Valid @RequestBody SignupRequest request) {
        adminService.superAdminSignup(request);

        // 🟢 TEST_SUPER_ADMIN_CREATE_SUCCESS ("테스트용 슈퍼 관리자 계정이 추가되었습니다.") 사용!
        return ResponseEntity
                .status(SuccessCode.TEST_SUPER_ADMIN_CREATE_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.TEST_SUPER_ADMIN_CREATE_SUCCESS, null));
    }

    @Operation(
            summary = "로그인",
            description = "관리자 계정으로 로그인하여 세션을 생성합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호 불일치",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "403", description = "승인되지 않은 계정",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpSession session) {

        SessionUser sessionUser = adminService.login(loginRequest);

        // 세션 정보 저장, 만료 기간 설정(1시간)
        session.setAttribute("userInfo", sessionUser);
        session.setMaxInactiveInterval(SESSION_EXPIRATION);

        // 🟢 LOGIN_SUCCESS ("로그인에 성공하였습니다.") 사용!
        return ResponseEntity
                .status(SuccessCode.LOGIN_SUCCESS.getHttpStatus())
                .body(ApiResponse.of(SuccessCode.LOGIN_SUCCESS, null));
    }

    @Operation(
            summary = "로그아웃",
            description = "현재 로그인한 관리자의 세션을 종료합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    })
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {

        session.invalidate();

        // 헤더의 cache 비우고 재검증 요구 및 즉시 만료 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.setPragma("no-cache");
        headers.setExpires(0);

        // JSESSIONID 쿠키 값 null로 설정 및 즉시 만료
        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", "")
                .path("/")
                .maxAge(0)
                .secure(true)
                .httpOnly(true)
                .build();

        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        // 🟢 LOGOUT_SUCCESS ("로그아웃되었습니다.") 사용!
        return ResponseEntity
                .status(SuccessCode.LOGOUT_SUCCESS.getHttpStatus())
                .headers(headers)
                .body(ApiResponse.of(SuccessCode.LOGOUT_SUCCESS, null));
    }
}