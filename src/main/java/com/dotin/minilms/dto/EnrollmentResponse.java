package com.dotin.minilms.dto;

public class EnrollmentResponse {
    private Long id;
    private String username;
    private Long roadmapId;
    private String roadmapTitle;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Long getRoadmapId() { return roadmapId; }
    public void setRoadmapId(Long roadmapId) { this.roadmapId = roadmapId; }

    public String getRoadmapTitle() { return roadmapTitle; }
    public void setRoadmapTitle(String roadmapTitle) { this.roadmapTitle = roadmapTitle; }
}
