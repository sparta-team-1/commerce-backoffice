package org.example.commercebackoffice.admin.repository;

import jakarta.persistence.Id;
import org.example.commercebackoffice.admin.controller.dto.AdminInfoForDashboard;
import org.example.commercebackoffice.admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByEmail(String email); //가입할 때 이메일 중복 체크를 위해 사용
    Optional<Admin> findByEmail(String email);
    //전체 관리자 계정 수 및 활성 관리자 계정 수 조회
    @Query("""
        SELECT new org.example.commercebackoffice.admin.controller.dto.AdminInfoForDashboard(
            COUNT(a),
            COUNT(CASE WHEN a.status = 'ACTIVE' THEN 1 END)
        )
        FROM Admin a
    """)
    AdminInfoForDashboard countAllAdminsAndCountActiveAdmins();
}
