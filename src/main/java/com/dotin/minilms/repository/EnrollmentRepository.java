package com.dotin.minilms.repository;

import com.dotin.minilms.entity.Enrollment;
import com.dotin.minilms.entity.Roadmap;
import com.dotin.minilms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByUserAndRoadmap(User user, Roadmap roadmap);
    List<Enrollment> findByUser(User user);
}
