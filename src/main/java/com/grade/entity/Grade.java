package com.grade.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "grades", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "subject_id", "semester", "academic_year"})
})
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    
    @Column(nullable = false, precision = 5, scale = 2)
    @NotNull(message = "成绩不能为空")
    @DecimalMin(value = "0.00", message = "成绩不能小于0")
    @DecimalMax(value = "100.00", message = "成绩不能大于100")
    private BigDecimal score;
    
    @Column(name = "grade_level")
    private String gradeLevel; // 等级（优秀、良好、中等、及格、不及格）
    
    @Column(nullable = false)
    private String semester; // 学期（如：2023-1, 2023-2）
    
    @Column(name = "academic_year", nullable = false)
    private String academicYear; // 学年（如：2023-2024）
    
    @Column(name = "exam_type")
    private String examType; // 考试类型（期中、期末、补考等）
    
    // 新增缺失的字段
    @Column
    private String remarks; // 备注
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy; // 录入人
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        calculateGradeLevel();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        calculateGradeLevel();
    }
    
    private void calculateGradeLevel() {
        if (score != null) {
            double scoreValue = score.doubleValue();
            if (scoreValue >= 90) {
                gradeLevel = "优秀";
            } else if (scoreValue >= 80) {
                gradeLevel = "良好";
            } else if (scoreValue >= 70) {
                gradeLevel = "中等";
            } else if (scoreValue >= 60) {
                gradeLevel = "及格";
            } else {
                gradeLevel = "不及格";
            }
        }
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    
    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }
    
    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }
    
    public String getGradeLevel() { return gradeLevel; }
    public void setGradeLevel(String gradeLevel) { this.gradeLevel = gradeLevel; }
    
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    
    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    
    public String getExamType() { return examType; }
    public void setExamType(String examType) { this.examType = examType; }
    
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // 新增缺失的getter和setter方法
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}