package org.example.commercebackoffice.admin.controller.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.example.commercebackoffice.admin.domain.enums.AdminRole;

@Schema(description = "회원가입 요청 DTO")
@Getter
public class SignupRequest {
    @Schema(description = "이름", example = "test")
    @NotBlank(message = "이름은 필수로 입력해야됩니다")
    private String name;

    @Schema(description = "이메일", example = "test@email.com")
    @NotBlank(message = "이메일은 필수로 입력해야됩니다")
    @Email(message =  "올바른 이메일 형식이 아닙니다" )
    private String email;

    @Schema(description = "비밀번호", example = "password")
    @NotBlank(message = "비밀번호는 필수로 입력해야됩니다")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상")
    private String password;

    @Schema(description = "전화번호", example = "010-1234-5678")
    @NotBlank(message = "전화번호는 필수로 입력해야됩니다")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$" , message = "전화번호는 010-0000-0000 형식이어야 됩니다")
    private String phoneNumber;

    @Schema(description = "역할", example = "CS_ADMIN")
    @NotNull(message = "역할은 필수로 선택해야됩니다")
    private AdminRole role; //super, operator, cs 등
}
