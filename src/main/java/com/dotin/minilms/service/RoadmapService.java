package com.dotin.minilms.service;

import com.dotin.minilms.entity.Roadmap;
import com.dotin.minilms.repository.RoadmapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoadmapService {

    private final RoadmapRepository roadmapRepository;

    // تزریق وابستگی (Dependency Injection) از طریق سازنده
    public RoadmapService(RoadmapRepository roadmapRepository) {
        this.roadmapRepository = roadmapRepository;
    }

    // متد برای ایجاد رودمپ جدید (توسط ادمین)
    public Roadmap createRoadmap(String title, String description) {
        Roadmap roadmap = new Roadmap(title, description);
        return roadmapRepository.save(roadmap);
    }

    // متد برای ویرایش رودمپ موجود (توسط ادمین)
    public Roadmap updateRoadmap(Long id, String title, String description) {
        Roadmap roadmap = roadmapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("رودمپ با این شناسه پیدا نشد!"));

        roadmap.setTitle(title);
        roadmap.setDescription(description);
        return roadmapRepository.save(roadmap);
    }
    public void deleteRoadmap(Long id) {
        // بررسی اینکه آیا رودمپ با این ID وجود دارد یا خیر (اختیاری ولی استاندارد)
        if (!roadmapRepository.existsById(id)) {
            throw new RuntimeException("رودمپ مورد نظر یافت نشد!");
        }
        roadmapRepository.deleteById(id);
    }

    // متد برای دیدن لیست تمام رودمپ‌ها
    public List<Roadmap> getAllRoadmaps() {
        return roadmapRepository.findAll();
    }
}
