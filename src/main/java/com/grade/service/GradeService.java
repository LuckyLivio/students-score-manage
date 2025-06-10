package com.grade.service;

import com.grade.entity.Grade;
import com.grade.entity.Subject;
import com.grade.entity.User;
import com.grade.repository.GradeRepository;
import com.grade.repository.SubjectRepository;
import com.grade.repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class GradeService {
    
    @Autowired
    private GradeRepository gradeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    public Grade saveGrade(Grade grade) {
        // 检查是否已存在相同记录
        Optional<Grade> existing = gradeRepository.findByStudentIdAndSubjectIdAndSemesterAndAcademicYear(
            grade.getStudent().getId(), 
            grade.getSubject().getId(), 
            grade.getSemester(), 
            grade.getAcademicYear()
        );
        
        if (existing.isPresent() && !existing.get().getId().equals(grade.getId())) {
            throw new RuntimeException("该学生在此学期的该科目成绩已存在");
        }
        
        return gradeRepository.save(grade);
    }
    
    public List<Grade> findAllGrades() {
        return gradeRepository.findAll();
    }
    
    public List<Grade> findGradesByClass(Long classId) {
        return gradeRepository.findByClassId(classId);
    }
    
    public List<Grade> findGradesByClassAndSubjectAndSemester(Long classId, Long subjectId, String semester) {
        return gradeRepository.findByClassIdAndSubjectIdAndSemester(classId, subjectId, semester);
    }
    
    public Optional<Grade> findById(Long id) {
        return gradeRepository.findById(id);
    }
    
    public Grade updateGrade(Grade grade) {
        return gradeRepository.save(grade);
    }
    
    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }
    
    public void batchSaveGrades(List<Long> studentIds, Long subjectId, List<String> scores, 
                              String semester, String academicYear, List<String> examDates, 
                              List<String> remarks, User createdBy) {
        for (int i = 0; i < studentIds.size(); i++) {
            if (scores.get(i) != null && !scores.get(i).trim().isEmpty()) {
                Grade grade = new Grade();
                grade.setStudent(userRepository.findById(studentIds.get(i)).orElse(null));
                grade.setSubject(subjectRepository.findById(subjectId).orElse(null));
                grade.setScore(new BigDecimal(scores.get(i)));
                grade.setSemester(semester);
                grade.setAcademicYear(academicYear);
                grade.setCreatedBy(createdBy);
                
                if (examDates != null && i < examDates.size() && examDates.get(i) != null) {
                    // 处理考试日期
                }
                
                if (remarks != null && i < remarks.size() && remarks.get(i) != null) {
                    grade.setRemarks(remarks.get(i));
                }
                
                saveGrade(grade);
            }
        }
    }
    
    public List<Grade> findGradesByStudent(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }
    
    public List<Grade> findGradesByStudentAndSemester(Long studentId, String semester) {
        return gradeRepository.findByStudentIdAndSemester(studentId, semester);
    }
    
    // 删除重复的 findGradesByClass 方法（第107行）
    
    public List<Grade> findGradesByMajor(Long majorId) {
        return gradeRepository.findByMajorId(majorId);
    }
    
    public List<Grade> findGradesByDepartment(Long departmentId) {
        return gradeRepository.findByDepartmentId(departmentId);
    }
    
    public Map<String, Object> getGradeStatistics(Long subjectId, String semester) {
        Map<String, Object> stats = new HashMap<>();
        
        Optional<BigDecimal> avgScore = gradeRepository.findAverageScoreBySubjectAndSemester(subjectId, semester);
        Optional<BigDecimal> maxScore = gradeRepository.findMaxScoreBySubjectAndSemester(subjectId, semester);
        Optional<BigDecimal> minScore = gradeRepository.findMinScoreBySubjectAndSemester(subjectId, semester);
        Long failedCount = gradeRepository.countFailedBySubjectAndSemester(subjectId, semester);
        
        stats.put("averageScore", avgScore.orElse(BigDecimal.ZERO));
        stats.put("maxScore", maxScore.orElse(BigDecimal.ZERO));
        stats.put("minScore", minScore.orElse(BigDecimal.ZERO));
        stats.put("failedCount", failedCount);
        
        return stats;
    }
    
    public List<Grade> importGradesFromExcel(MultipartFile file, User createdBy) throws IOException {
        List<Grade> grades = new ArrayList<>();
        
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            
            // 跳过标题行
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                try {
                    String studentId = getCellStringValue(row.getCell(0));
                    String subjectName = getCellStringValue(row.getCell(1));
                    String scoreStr = getCellStringValue(row.getCell(2));
                    String semester = getCellStringValue(row.getCell(3));
                    String academicYear = getCellStringValue(row.getCell(4));
                    
                    if (studentId.isEmpty() || subjectName.isEmpty() || scoreStr.isEmpty()) {
                        continue;
                    }
                    
                    Optional<User> student = userRepository.findByStudentId(studentId);
                    Optional<Subject> subject = subjectRepository.findByName(subjectName);
                    
                    if (student.isPresent() && subject.isPresent()) {
                        Grade grade = new Grade();
                        grade.setStudent(student.get());
                        grade.setSubject(subject.get());
                        grade.setScore(new BigDecimal(scoreStr));
                        grade.setSemester(semester);
                        grade.setAcademicYear(academicYear);
                        grade.setCreatedBy(createdBy);
                        
                        grades.add(saveGrade(grade));
                    }
                } catch (Exception e) {
                    // 记录错误但继续处理其他行
                    System.err.println("处理第" + (i + 1) + "行时出错: " + e.getMessage());
                }
            }
        }
        
        return grades;
    }
    
    private String getCellStringValue(Cell cell) {
        if (cell == null) return "";
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            default:
                return "";
        }
    }
}