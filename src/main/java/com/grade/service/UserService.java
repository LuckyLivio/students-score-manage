package com.grade.service;

import com.grade.entity.User;
import com.grade.entity.UserRole;
import com.grade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        
        if (user.getStudentId() != null && userRepository.existsByStudentId(user.getStudentId())) {
            throw new RuntimeException("学号已存在");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    public User updateUser(User user) {
        Optional<User> existing = userRepository.findById(user.getId());
        if (existing.isPresent()) {
            User existingUser = existing.get();
            existingUser.setRealName(user.getRealName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhone(user.getPhone());
            existingUser.setRole(user.getRole());
            existingUser.setStudentClass(user.getStudentClass());
            
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            
            return userRepository.save(existingUser);
        }
        throw new RuntimeException("用户不存在");
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> findByStudentId(String studentId) {
        return userRepository.findByStudentId(studentId);
    }
    
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    
    public List<User> findStudentsByClass(Long classId) {
        return userRepository.findByStudentClassIdAndRole(classId, UserRole.STUDENT);
    }
    
    public List<User> findUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }
    
    public List<User> findStudentsByDepartment(Long departmentId) {
        return userRepository.findStudentsByDepartment(departmentId);
    }
    
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}