package com.project.pc.repository;

import com.project.pc.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findById(long id);
    Optional<Activity> findByName(String name);
}
