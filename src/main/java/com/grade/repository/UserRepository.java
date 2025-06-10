package com.grade.repository;

import com.grade.entity.User;
import com.grade.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByStudentId(String studentId);
    List<User> findByRole(UserRole role);
    List<User> findByStudentClassId(Long classId);
    
    // 新增缺失的方法
    List<User> findByStudentClassIdAndRole(Long classId, UserRole role);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    Long countByRole(@Param("role") UserRole role);
    
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.enabled = true")
    List<User> findActiveUsersByRole(@Param("role") UserRole role);
    
    @Query("SELECT u FROM User u WHERE u.studentClass.major.department.id = :departmentId AND u.role = 'STUDENT'")
    List<User> findStudentsByDepartment(@Param("departmentId") Long departmentId);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.studentClass.major.department.id = :departmentId AND u.role = 'STUDENT'")
    Long countStudentsByDepartment(@Param("departmentId") Long departmentId);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.studentClass.major.id = :majorId AND u.role = 'STUDENT'")
    Long countStudentsByMajor(@Param("majorId") Long majorId);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.studentClass.id = :classId AND u.role = 'STUDENT'")
    Long countStudentsByClass(@Param("classId") Long classId);
    
    boolean existsByUsername(String username);
    boolean existsByStudentId(String studentId);
}