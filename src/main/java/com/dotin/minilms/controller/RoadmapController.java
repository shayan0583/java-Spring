package com.dotin.minilms.controller;

import com.dotin.minilms.entity.Roadmap;
import com.dotin.minilms.service.RoadmapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roadmaps")
public class RoadmapController {

    private final RoadmapService roadmapService;

    public RoadmapController(RoadmapService roadmapService) {
        this.roadmapService  = roadmapService;
    }

    // ساخت یک رودمپ جدید با دریافت JSON از طریق @RequestBody
    @PostMapping
    public ResponseEntity<Roadmap> createRoadmap(@RequestBody RoadmapRequest request) {
        Roadmap newRoadmap = roadmapService.createRoadmap(request.getTitle(), request.getDescription());
        return new ResponseEntity<>(newRoadmap, HttpStatus.CREATED);
    }

    // دریافت لیست تمام رودمپ‌ها
    @GetMapping
    public ResponseEntity<List<Roadmap>> getAllRoadmaps() {
        List<Roadmap> roadmaps = roadmapService.getAllRoadmaps();
        return new ResponseEntity<>(roadmaps, HttpStatus.OK);
    }

    // ویرایش یک رودمپ موجود با دریافت JSON
    @PutMapping("/{id}")
    public ResponseEntity<Roadmap> updateRoadmap(@PathVariable Long id, @RequestBody RoadmapRequest request) {
        Roadmap updatedRoadmap = roadmapService.updateRoadmap(id, request.getTitle(), request.getDescription());
        return new ResponseEntity<>(updatedRoadmap, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoadmap(@PathVariable Long id) {
        roadmapService.deleteRoadmap(id);
        return ResponseEntity.noContent().build();
    }

    // یک کلاس کمکی (DTO) ساده درون خود کنترلر برای دریافت داده‌ها
    public static class RoadmapRequest {
        private String title;
        private String description;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}
