package com.grade.service;

import com.grade.entity.Subject;
import com.grade.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubjectService {
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    public List<Subject> findAllSubjects() {
        return subjectRepository.findAll();
    }
    
    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }
    
    public Optional<Subject> findByName(String name) {
        return subjectRepository.findByName(name);
    }
    
    public Subject createSubject(Subject subject) {
        if (subjectRepository.existsByName(subject.getName())) {
            throw new RuntimeException("科目名称已存在");
        }
        return subjectRepository.save(subject);
    }
    
    public Subject updateSubject(Subject subject) {
        Optional<Subject> existing = subjectRepository.findByName(subject.getName());
        if (existing.isPresent() && !existing.get().getId().equals(subject.getId())) {
            throw new RuntimeException("科目名称已存在");
        }
        return subjectRepository.save(subject);
    }
    
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}