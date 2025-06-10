package com.grade.service;

import com.grade.entity.UserRole;
import com.grade.repository.GradeRepository;
import com.grade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsService {
    
    @Autowired
    private GradeRepository gradeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Map<String, Object> getStatistics(String semester, Long departmentId, Long majorId, Long classId) {
        Map<String, Object> stats = new HashMap<>();
        
        // 基础统计 - 修复类型错误
        stats.put("totalStudents", userRepository.countByRole(UserRole.STUDENT));
        stats.put("totalTeachers", userRepository.countByRole(UserRole.TEACHER));
        stats.put("totalGrades", gradeRepository.count());
        
        // 成绩统计
        if (semester != null) {
            stats.put("averageScore", gradeRepository.findAverageScoreBySemester(semester));
            stats.put("maxScore", gradeRepository.findMaxScoreBySemester(semester));
            stats.put("minScore", gradeRepository.findMinScoreBySemester(semester));
            stats.put("passRate", gradeRepository.findPassRateBySemester(semester, new BigDecimal("60")));
        }
        
        // 按院系统计
        if (departmentId != null) {
            stats.put("departmentAverageScore", gradeRepository.findAverageScoreByDepartment(departmentId));
            stats.put("departmentStudentCount", userRepository.countStudentsByDepartment(departmentId));
        }
        
        // 按专业统计
        if (majorId != null) {
            stats.put("majorAverageScore", gradeRepository.findAverageScoreByMajor(majorId));
            stats.put("majorStudentCount", userRepository.countStudentsByMajor(majorId));
        }
        
        // 按班级统计
        if (classId != null) {
            stats.put("classAverageScore", gradeRepository.findAverageScoreByClass(classId));
            stats.put("classStudentCount", userRepository.countStudentsByClass(classId));
        }
        
        return stats;
    }
}