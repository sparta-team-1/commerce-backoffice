package org.example.commercebackoffice.admin.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.commercebackoffice.admin.domain.enums.AdminRole;
import org.example.commercebackoffice.admin.domain.enums.AdminStatus;
import org.example.commercebackoffice.config.PasswordEncoder;
import org.example.commercebackoffice.common.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@Table(name = "admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false , unique = true ,length = 100)
    private String email;

    @Column(nullable = false,length = 255)
    private String password;
    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private AdminRole role;

    @Enumerated(EnumType.STRING) //db에 저장 시 문자열 그대로 저장 -> 유지보수성 향상
    @Column(nullable = false)
    private AdminStatus status;
    @Column(nullable = false)
    private LocalDateTime approvedAt;

    private LocalDateTime rejectedAt;
    private String rejectionReason;

    //빌더 패턴 적용
    @Builder
    public Admin(String email, String name, String password, String phoneNumber, AdminRole role, AdminStatus status) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
    }

    //비밀번호 검증 로직
    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.password);
    }

    //계정 승인 로직
    public void approve() {
        this.approvedAt = LocalDateTime.now();
        this.status = AdminStatus.ACTIVE;
    }

    //계정 거부 로직
    public void reject(String rejectionReason) {
        this.rejectedAt = LocalDateTime.now();
        this.rejectionReason = rejectionReason;
        this.status = AdminStatus.REJECTED;
    }

    //계정 삭제 로직
    public void delete() {
        super.delete();
        this.status = AdminStatus.SUSPENDED;
    }

    //상태 변경 로직
    public void changeStatus(AdminStatus status) {
        this.status = status;
    }

    //역할 변경 로직
    public void changeRole(AdminRole role) {
        this.role = role;
    }

    //이름 변경 로직
    public void changeName(String name) {
        this.name = name;
    }

    //이메일 변경 로직
    public void changeEmail(String email) {
        this.email = email;
    }

    //전화번호 변경 로직
    public void changePhone(String phone) {
        this.phoneNumber = phone;
    }

    //super 계정 확인 로직
    public boolean isSuperAdmin() {
        return this.role == AdminRole.SUPER_ADMIN;
    }
}