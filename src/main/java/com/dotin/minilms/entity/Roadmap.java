package com.dotin.minilms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roadmaps")
public class Roadmap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // سازنده پیش‌فرض (الزامی برای JPA)
    public Roadmap() {
    }

    // سازنده برای ساخت سریع شیء
    public Roadmap(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Getter و Setterها
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
