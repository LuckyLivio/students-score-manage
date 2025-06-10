package com.grade.controller;

import com.grade.entity.Grade;
import com.grade.entity.User;
import com.grade.service.GradeService;
import com.grade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {
    
    @Autowired
    private GradeService gradeService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/grades")
    public String viewGrades(Authentication auth, 
                           @RequestParam(required = false) String semester,
                           Model model) {
        Optional<User> currentUser = userService.findByUsername(auth.getName());
        if (currentUser.isPresent()) {
            List<Grade> grades;
            if (semester != null && !semester.isEmpty()) {
                grades = gradeService.findGradesByStudentAndSemester(currentUser.get().getId(), semester);
            } else {
                grades = gradeService.findGradesByStudent(currentUser.get().getId());
            }
            model.addAttribute("grades", grades);
            model.addAttribute("student", currentUser.get());
            model.addAttribute("selectedSemester", semester);
        }
        return "student/grades";
    }
}