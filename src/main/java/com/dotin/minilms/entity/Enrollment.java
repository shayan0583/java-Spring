package com.dotin.minilms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "enrollments",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_roadmap",
                        columnNames = {"user_id", "roadmap_id"}
                )
        }
)
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roadmap_id", nullable = false)
    private Roadmap roadmap;

    @Column(name = "enrolled_at", nullable = false)
    private LocalDateTime enrolledAt;

    // سازنده پیش‌فرض (الزامی برای JPA)
    public Enrollment() {
    }

    // سازنده با پارامتر
    public Enrollment(User user, Roadmap roadmap) {
        this.user = user;
        this.roadmap = roadmap;
        this.enrolledAt = LocalDateTime.now();
    }

    // هنگام ذخیره اولیه، اگر زمان ست نشده بود، زمان فعلی را می‌گذارد
    @PrePersist
    public void prePersist() {
        if (this.enrolledAt == null) {
            this.enrolledAt = LocalDateTime.now();
        }
    }

    // Getter و Setterها
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Roadmap getRoadmap() {
        return roadmap;
    }

    public void setRoadmap(Roadmap roadmap) {
        this.roadmap = roadmap;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }
}
