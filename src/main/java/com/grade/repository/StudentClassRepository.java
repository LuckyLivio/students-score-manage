package com.grade.repository;

import com.grade.entity.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {
    Optional<StudentClass> findByName(String name);
    List<StudentClass> findByMajorId(Long majorId);
    List<StudentClass> findByGradeYear(Integer gradeYear);
    boolean existsByName(String name);
}