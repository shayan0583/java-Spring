package com.dotin.minilms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TopicRequestDTO {

    @NotBlank(message = "عنوان تاپیک نمی‌تواند خالی باشد")
    private String title;

    @NotNull(message = "شماره ترتیب الزامی است")
    @Min(value = 1, message = "شماره ترتیب باید حداقل ۱ باشد")
    private Integer orderNumber;

    @NotNull(message = "مدت زمان الزامی است")
    @Min(value = 1, message = "مدت زمان باید حداقل ۱ دقیقه باشد")
    private Integer duration;

    // Getters and Setters
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
}
