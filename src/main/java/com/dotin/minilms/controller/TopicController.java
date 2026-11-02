package com.dotin.minilms.controller;

import com.dotin.minilms.dto.TopicRequestDTO;
import com.dotin.minilms.dto.TopicResponseDTO;
import com.dotin.minilms.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    // ۱. دریافت تمام تاپیک‌های یک رودمپ (هم ادمین، هم دانشجو)
    @GetMapping("/roadmaps/{roadmapId}/topics")
    public ResponseEntity<List<TopicResponseDTO>> getTopicsByRoadmap(@PathVariable Long roadmapId) {
        List<TopicResponseDTO> topics = topicService.getTopicsByRoadmapId(roadmapId);
        return ResponseEntity.ok(topics);
    }

    // ۲. ایجاد تاپیک جدید برای رودمپ (فقط ادمین)
    @PostMapping("/roadmaps/{roadmapId}/topics")
    public ResponseEntity<TopicResponseDTO> createTopic(
            @PathVariable Long roadmapId,
            @Valid @RequestBody TopicRequestDTO requestDTO) {
        TopicResponseDTO createdTopic = topicService.createTopic(roadmapId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTopic);
    }

    // ۳. ویرایش تاپیک (فقط ادمین)
    @PutMapping("/topics/{topicId}")
    public ResponseEntity<TopicResponseDTO> updateTopic(
            @PathVariable Long topicId,
            @Valid @RequestBody TopicRequestDTO requestDTO) {
        TopicResponseDTO updatedTopic = topicService.updateTopic(topicId, requestDTO);
        return ResponseEntity.ok(updatedTopic);
    }

    // ۴. حذف تاپیک (فقط ادمین)
    @DeleteMapping("/topics/{topicId}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopic(topicId);
        return ResponseEntity.noContent().build();
    }
}
