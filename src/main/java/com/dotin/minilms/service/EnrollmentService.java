package com.dotin.minilms.service;

import com.dotin.minilms.dto.EnrollmentResponse;
import com.dotin.minilms.entity.Enrollment;
import com.dotin.minilms.entity.Roadmap;
import com.dotin.minilms.entity.User;
import com.dotin.minilms.repository.EnrollmentRepository;
import com.dotin.minilms.repository.RoadmapRepository;
import com.dotin.minilms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final RoadmapRepository roadmapRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             UserRepository userRepository,
                             RoadmapRepository roadmapRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.roadmapRepository = roadmapRepository;
    }

    // ثبت‌نام دانشجو در رودمپ (جلوگیری از ثبت‌نام تکراری)
    public EnrollmentResponse enrollStudent(String username, Long roadmapId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("کاربر یافت نشد: " + username));

        Roadmap roadmap = roadmapRepository.findById(roadmapId)
                .orElseThrow(() -> new RuntimeException("رودمپ یافت نشد با شناسه: " + roadmapId));

        // بررسی ثبت‌نام تکراری
        boolean alreadyEnrolled = enrollmentRepository.existsByUserAndRoadmap(user, roadmap);
        if (alreadyEnrolled) {
            throw new RuntimeException("این دانشجو قبلاً در این رودمپ ثبت‌نام کرده است!");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setRoadmap(roadmap);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return mapToDto(savedEnrollment);
    }

    // گرفتن لیست ثبت‌نام‌های یک کاربر خاص (مخصوص دانشجو)
    public List<EnrollmentResponse> getEnrollmentsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("کاربر یافت نشد: " + username));

        List<Enrollment> enrollments = enrollmentRepository.findByUser(user);
        return enrollments.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // گرفتن کل ثبت‌نام‌های سیستم (مخصوص ادمین)
    public List<EnrollmentResponse> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // تبدیل موجودیت Enrollment به DTO برای ارسال امن به فرانت‌اند
    private EnrollmentResponse mapToDto(Enrollment enrollment) {
        EnrollmentResponse dto = new EnrollmentResponse();
        dto.setId(enrollment.getId());
        dto.setUsername(enrollment.getUser().getUsername());
        dto.setRoadmapId(enrollment.getRoadmap().getId());
        dto.setRoadmapTitle(enrollment.getRoadmap().getTitle());
        return dto;
    }
}
