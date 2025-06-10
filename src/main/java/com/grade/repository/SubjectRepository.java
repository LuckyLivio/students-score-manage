package com.grade.repository;

import com.grade.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByName(String name);
    Optional<Subject> findBySubjectCode(String subjectCode);
    boolean existsByName(String name);
    boolean existsBySubjectCode(String subjectCode);
}