package com.project.pc.repository;

import com.project.pc.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Long, Task> {
    Optional<Task> findById(Long id);
}
