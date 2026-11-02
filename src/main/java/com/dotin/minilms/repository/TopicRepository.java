package com.dotin.minilms.repository;

import com.dotin.minilms.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    // دریافت تمام تاپیک‌های یک رودمپ به ترتیب شماره ترتیب (orderNumber)
    List<Topic> findByRoadmapIdOrderByOrderNumberAsc(Long roadmapId);

    // بررسی اینکه آیا تاپیکی با این شماره ترتیب در این رودمپ وجود دارد یا خیر (برای جلوگیری از تکرار)
    boolean existsByRoadmapIdAndOrderNumber(Long roadmapId, Integer orderNumber);

    // بررسی تکراری نبودن هنگام ویرایش (به جز خود تاپیک جاری)
    boolean existsByRoadmapIdAndOrderNumberAndIdNot(Long roadmapId, Integer orderNumber, Long id);
}
