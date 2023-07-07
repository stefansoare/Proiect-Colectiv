package com.project.pc.repository;

import com.project.pc.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(Long id);
    List<Task> findByActivityId(Long id);
    List<Task> findByStudentId(Long id);
}
