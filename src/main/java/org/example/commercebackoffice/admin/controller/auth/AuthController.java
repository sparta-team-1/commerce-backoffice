package org.example.commercebackoffice.admin.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
import org.example.commercebackoffice.common.exception.ErrorResponse;
import org.example.commercebackoffice.common.message.SuccessCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증", description = "관리자 인증 및 로그인 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AdminService adminService;
    private final static int SESSION_EXPIRATION = 3600;

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "회원가입 신청 성공",
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                {
                                  "success": true,
                                  "message": "회원가입 신청이 완료되었습니다.",
                                  "data": null
                                }
                                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "USER_STATUS_NOT_PENDING",
                                    value = """
                                            {
                                              "status": 400,
                                              "message": "USER IS NOT PENDING",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("/register")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<Void>> signup(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "관리자 회원가입 요청",
                    required = true
            )
            @Valid @RequestBody SignupRequest request
    ) {
        adminService.signup(request);

        return ResponseEntity
                .status(SuccessCode.ADMIN_SIGNUP_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.ADMIN_SIGNUP_SUCCESS, null));
    }

    //테스트용 슈퍼 관리자 추가
    @PostMapping("/register/super")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<Void>> superAdminSignup(@Valid @RequestBody SignupRequest request) {
        adminService.superAdminSignup(request);

        // 🟢 TEST_SUPER_ADMIN_CREATE_SUCCESS ("테스트용 슈퍼 관리자 계정이 추가되었습니다.") 사용!
        return ResponseEntity
                .status(SuccessCode.TEST_SUPER_ADMIN_CREATE_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.TEST_SUPER_ADMIN_CREATE_SUCCESS, null));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                {
                                  "success": true,
                                  "message": "로그인에 성공하였습니다.",
                                  "data": null
                                }
                                """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "이메일 또는 비밀번호 불일치",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "PASSWORD_INCORRECT",
                                    value = """
                                            {
                                              "status": 400,
                                              "message": "PASSWORD INCORRECT",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "승인되지 않은 계정",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "USER_NOT_ACTIVE",
                                    value = """
                                            {
                                              "status": 401,
                                              "message": "USER NOT ACTIVE",
                                              "time": "2026-07-18T00:00:00.00000"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<Void>> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpSession session) {

        SessionUser sessionUser = adminService.login(loginRequest);

        // 세션 정보 저장, 만료 기간 설정(1시간)
        session.setAttribute("userInfo", sessionUser);
        session.setMaxInactiveInterval(SESSION_EXPIRATION);

        // 🟢 LOGIN_SUCCESS ("로그인에 성공하였습니다.") 사용!
        return ResponseEntity
                .status(SuccessCode.LOGIN_SUCCESS.getHttpStatus())
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.LOGIN_SUCCESS, null));
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그아웃 성공",
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                {
                                  "success": true,
                                  "message": "로그아웃되었습니다.",
                                  "data": null
                                }
                                """
                            )
                    )
            )
    })
    @PostMapping("/logout")
    public ResponseEntity<org.example.commercebackoffice.common.dto.ApiResponse<Void>> logout(HttpSession session) {

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
                .body(org.example.commercebackoffice.common.dto.ApiResponse
                        .of(SuccessCode.LOGOUT_SUCCESS, null));
    }
}