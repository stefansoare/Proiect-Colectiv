package com.project.pc.repository;

import com.project.pc.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentById(long id);
    List<Student> findStudentByName(String name);
    Optional<Student> findStudentByEmail(String email);
}
