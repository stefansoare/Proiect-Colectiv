package com.project.pc.repository;

import com.project.pc.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByTaskIdAndStudentId(Long tId, Long sId);
    List<Grade> findByStudentId(Long sId);
}
