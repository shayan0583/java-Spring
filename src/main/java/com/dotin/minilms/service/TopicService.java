package com.dotin.minilms.service;

import com.dotin.minilms.dto.TopicRequestDTO;
import com.dotin.minilms.dto.TopicResponseDTO;
import com.dotin.minilms.entity.Roadmap;
import com.dotin.minilms.entity.Topic;
import com.dotin.minilms.repository.RoadmapRepository;
import com.dotin.minilms.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final RoadmapRepository roadmapRepository;

    public TopicService(TopicRepository topicRepository, RoadmapRepository roadmapRepository) {
        this.topicRepository = topicRepository;
        this.roadmapRepository = roadmapRepository;
    }

    // ۱. دریافت تمام تاپیک‌های یک رودمپ
    public List<TopicResponseDTO> getTopicsByRoadmapId(Long roadmapId) {
        // بررسی وجود رودمپ
        if (!roadmapRepository.existsById(roadmapId)) {
            throw new RuntimeException("رودمپ مورد نظر یافت نشد!");
        }

        List<Topic> topics = topicRepository.findByRoadmapIdOrderByOrderNumberAsc(roadmapId);
        return topics.stream()
                .map(TopicResponseDTO::new)
                .collect(Collectors.toList());
    }

    // ۲. ایجاد تاپیک جدید
    @Transactional
    public TopicResponseDTO createTopic(Long roadmapId, TopicRequestDTO requestDTO) {
        Roadmap roadmap = roadmapRepository.findById(roadmapId)
                .orElseThrow(() -> new RuntimeException("رودمپ مورد نظر یافت نشد!"));

        // بررسی تکراری نبودن شماره ترتیب در این رودمپ
        boolean exists = topicRepository.existsByRoadmapIdAndOrderNumber(roadmapId, requestDTO.getOrderNumber());
        if (exists) {
            throw new RuntimeException("تاپیکی با این شماره ترتیب قبلاً در این رودمپ ثبت شده است!");
        }

        Topic topic = new Topic(
                requestDTO.getTitle(),
                requestDTO.getOrderNumber(),
                requestDTO.getDuration(),
                roadmap
        );

        Topic savedTopic = topicRepository.save(topic);
        return new TopicResponseDTO(savedTopic);
    }

    // ۳. ویرایش تاپیک
    @Transactional
    public TopicResponseDTO updateTopic(Long topicId, TopicRequestDTO requestDTO) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("تاپیک مورد نظر یافت نشد!"));

        Long roadmapId = topic.getRoadmap().getId();

        // بررسی تکراری نبودن شماره ترتیب (به جز خود این تاپیک)
        boolean exists = topicRepository.existsByRoadmapIdAndOrderNumberAndIdNot(roadmapId, requestDTO.getOrderNumber(), topicId);
        if (exists) {
            throw new RuntimeException("تاپیک دیگری با این شماره ترتیب در این رودمپ وجود دارد!");
        }

        topic.setTitle(requestDTO.getTitle());
        topic.setOrderNumber(requestDTO.getOrderNumber());
        topic.setDuration(requestDTO.getDuration());

        Topic updatedTopic = topicRepository.save(topic);
        return new TopicResponseDTO(updatedTopic);
    }

    // ۴. حذف تاپیک
    @Transactional
    public void deleteTopic(Long topicId) {
        if (!topicRepository.existsById(topicId)) {
            throw new RuntimeException("تاپیک مورد نظر یافت نشد!");
        }
        topicRepository.deleteById(topicId);
    }
}
