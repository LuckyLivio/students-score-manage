package com.grade.service;

import com.grade.entity.StudentClass;
import com.grade.repository.StudentClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentClassService {
    
    @Autowired
    private StudentClassRepository studentClassRepository;
    
    public List<StudentClass> findAllClasses() {
        return studentClassRepository.findAll();
    }
    
    public Optional<StudentClass> findById(Long id) {
        return studentClassRepository.findById(id);
    }
    
    public Optional<StudentClass> findByName(String name) {
        return studentClassRepository.findByName(name);
    }
    
    public List<StudentClass> findByMajorId(Long majorId) {
        return studentClassRepository.findByMajorId(majorId);
    }
    
    public List<StudentClass> findByGradeYear(Integer gradeYear) {
        return studentClassRepository.findByGradeYear(gradeYear);
    }
    
    public StudentClass createClass(StudentClass studentClass) {
        if (studentClassRepository.existsByName(studentClass.getName())) {
            throw new RuntimeException("班级名称已存在");
        }
        return studentClassRepository.save(studentClass);
    }
    
    public StudentClass updateClass(StudentClass studentClass) {
        Optional<StudentClass> existing = studentClassRepository.findByName(studentClass.getName());
        if (existing.isPresent() && !existing.get().getId().equals(studentClass.getId())) {
            throw new RuntimeException("班级名称已存在");
        }
        return studentClassRepository.save(studentClass);
    }
    
    public void deleteClass(Long id) {
        studentClassRepository.deleteById(id);
    }
}