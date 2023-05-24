package com.project.pc.repository;

import com.project.pc.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import  java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(Long id);
    List<Task> findByActivityId(Long aId);
}
