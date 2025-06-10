package com.grade.service;

import com.grade.entity.Major;
import com.grade.repository.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MajorService {
    
    @Autowired
    private MajorRepository majorRepository;
    
    public List<Major> findAllMajors() {
        return majorRepository.findAll();
    }
    
    public Optional<Major> findById(Long id) {
        return majorRepository.findById(id);
    }
    
    public Optional<Major> findByName(String name) {
        return majorRepository.findByName(name);
    }
    
    public List<Major> findByDepartmentId(Long departmentId) {
        return majorRepository.findByDepartmentId(departmentId);
    }
    
    public Major createMajor(Major major) {
        if (majorRepository.existsByName(major.getName())) {
            throw new RuntimeException("专业名称已存在");
        }
        return majorRepository.save(major);
    }
    
    public Major updateMajor(Major major) {
        Optional<Major> existing = majorRepository.findByName(major.getName());
        if (existing.isPresent() && !existing.get().getId().equals(major.getId())) {
            throw new RuntimeException("专业名称已存在");
        }
        return majorRepository.save(major);
    }
    
    public void deleteMajor(Long id) {
        majorRepository.deleteById(id);
    }
}