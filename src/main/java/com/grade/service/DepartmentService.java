package com.grade.service;

import com.grade.entity.Department;
import com.grade.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentService {
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    public List<Department> findAllDepartments() {
        return departmentRepository.findAll();
    }
    
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }
    
    public Optional<Department> findByName(String name) {
        return departmentRepository.findByName(name);
    }
    
    public Department createDepartment(Department department) {
        if (departmentRepository.existsByName(department.getName())) {
            throw new RuntimeException("院系名称已存在");
        }
        return departmentRepository.save(department);
    }
    
    public Department updateDepartment(Department department) {
        Optional<Department> existing = departmentRepository.findByName(department.getName());
        if (existing.isPresent() && !existing.get().getId().equals(department.getId())) {
            throw new RuntimeException("院系名称已存在");
        }
        return departmentRepository.save(department);
    }
    
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}