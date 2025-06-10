package com.grade.repository;

import com.grade.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
    Optional<Major> findByName(String name);
    List<Major> findByDepartmentId(Long departmentId);
    boolean existsByName(String name);
}