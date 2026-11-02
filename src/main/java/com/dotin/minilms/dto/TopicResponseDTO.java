package com.dotin.minilms.dto;

import com.dotin.minilms.entity.Topic;

public class TopicResponseDTO {

    private Long id;
    private String title;
    private Integer orderNumber;
    private Integer duration;
    private Long roadmapId;

    public TopicResponseDTO(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.orderNumber = topic.getOrderNumber();
        this.duration = topic.getDuration();
        this.roadmapId = topic.getRoadmap().getId();
    }

    // Getters and Setters
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

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getRoadmapId() {
        return roadmapId;
    }

    public void setRoadmapId(Long roadmapId) {
        this.roadmapId = roadmapId;
    }
}
