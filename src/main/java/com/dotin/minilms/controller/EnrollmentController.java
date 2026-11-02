package com.dotin.minilms.controller;

import com.dotin.minilms.dto.EnrollmentResponse;
import com.dotin.minilms.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // ۱. متد ثبت‌نام دانشجو در یک رودمپ (با استفاده از Principal برای شناسایی کاربر لاگین‌شده)
    @PostMapping("/{roadmapId}")
    public ResponseEntity<EnrollmentResponse> enrollStudent(@PathVariable Long roadmapId, Principal principal) {
        String username = principal.getName();
        EnrollmentResponse response = enrollmentService.enrollStudent(username, roadmapId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ۲. دریافت لیست ثبت‌نام‌های خودِ کاربر لاگین‌شده (مخصوص دانشجو برای بررسی دوره‌های ثبت‌نام‌شده)
    @GetMapping("/my")
    public ResponseEntity<List<EnrollmentResponse>> getMyEnrollments(Principal principal) {
        String username = principal.getName();
        List<EnrollmentResponse> enrollments = enrollmentService.getEnrollmentsByUsername(username);
        return ResponseEntity.ok(enrollments);
    }

    // ۳. دریافت کل ثبت‌نام‌های سیستم (مخصوص پنل ادمین)
    @GetMapping("/all")
    public ResponseEntity<List<EnrollmentResponse>> getAllEnrollments() {
        List<EnrollmentResponse> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }
}
