package com.grade.repository;

import com.grade.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentId(Long studentId);
    List<Grade> findByStudentIdAndSemester(Long studentId, String semester);
    List<Grade> findBySubjectId(Long subjectId);
    
    @Query("SELECT g FROM Grade g WHERE g.student.studentClass.id = :classId")
    List<Grade> findByClassId(@Param("classId") Long classId);
    
    @Query("SELECT g FROM Grade g WHERE g.student.studentClass.id = :classId AND g.subject.id = :subjectId AND g.semester = :semester")
    List<Grade> findByClassIdAndSubjectIdAndSemester(@Param("classId") Long classId, @Param("subjectId") Long subjectId, @Param("semester") String semester);
    
    @Query("SELECT g FROM Grade g WHERE g.student.studentClass.major.id = :majorId")
    List<Grade> findByMajorId(@Param("majorId") Long majorId);
    
    @Query("SELECT g FROM Grade g WHERE g.student.studentClass.major.department.id = :departmentId")
    List<Grade> findByDepartmentId(@Param("departmentId") Long departmentId);
    
    @Query("SELECT AVG(g.score) FROM Grade g WHERE g.subject.id = :subjectId AND g.semester = :semester")
    Optional<BigDecimal> findAverageScoreBySubjectAndSemester(@Param("subjectId") Long subjectId, @Param("semester") String semester);
    
    @Query("SELECT MAX(g.score) FROM Grade g WHERE g.subject.id = :subjectId AND g.semester = :semester")
    Optional<BigDecimal> findMaxScoreBySubjectAndSemester(@Param("subjectId") Long subjectId, @Param("semester") String semester);
    
    @Query("SELECT MIN(g.score) FROM Grade g WHERE g.subject.id = :subjectId AND g.semester = :semester")
    Optional<BigDecimal> findMinScoreBySubjectAndSemester(@Param("subjectId") Long subjectId, @Param("semester") String semester);
    
    @Query("SELECT COUNT(g) FROM Grade g WHERE g.subject.id = :subjectId AND g.semester = :semester AND g.score < 60")
    Long countFailedBySubjectAndSemester(@Param("subjectId") Long subjectId, @Param("semester") String semester);
    
    // 新增统计方法
    @Query("SELECT AVG(g.score) FROM Grade g WHERE g.semester = :semester")
    Optional<BigDecimal> findAverageScoreBySemester(@Param("semester") String semester);
    
    @Query("SELECT MAX(g.score) FROM Grade g WHERE g.semester = :semester")
    Optional<BigDecimal> findMaxScoreBySemester(@Param("semester") String semester);
    
    @Query("SELECT MIN(g.score) FROM Grade g WHERE g.semester = :semester")
    Optional<BigDecimal> findMinScoreBySemester(@Param("semester") String semester);
    
    @Query("SELECT (COUNT(g) * 100.0 / (SELECT COUNT(g2) FROM Grade g2 WHERE g2.semester = :semester)) FROM Grade g WHERE g.semester = :semester AND g.score >= :passScore")
    Optional<BigDecimal> findPassRateBySemester(@Param("semester") String semester, @Param("passScore") BigDecimal passScore);
    
    @Query("SELECT AVG(g.score) FROM Grade g WHERE g.student.studentClass.major.department.id = :departmentId")
    Optional<BigDecimal> findAverageScoreByDepartment(@Param("departmentId") Long departmentId);
    
    @Query("SELECT AVG(g.score) FROM Grade g WHERE g.student.studentClass.major.id = :majorId")
    Optional<BigDecimal> findAverageScoreByMajor(@Param("majorId") Long majorId);
    
    @Query("SELECT AVG(g.score) FROM Grade g WHERE g.student.studentClass.id = :classId")
    Optional<BigDecimal> findAverageScoreByClass(@Param("classId") Long classId);
    
    Optional<Grade> findByStudentIdAndSubjectIdAndSemesterAndAcademicYear(
        Long studentId, Long subjectId, String semester, String academicYear);
}