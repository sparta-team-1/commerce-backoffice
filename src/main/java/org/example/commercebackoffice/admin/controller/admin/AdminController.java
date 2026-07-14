package org.example.commercebackoffice.admin.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.commercebackoffice.admin.service.AdminService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService adminService;
}
