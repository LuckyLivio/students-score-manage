package com.grade.controller;

import com.grade.entity.*;
import com.grade.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private GradeService gradeService;
    
    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private StudentClassService studentClassService;
    
    @Autowired
    private DepartmentService departmentService;
    
    @Autowired
    private MajorService majorService;
    
    @Autowired
    private StatisticsService statisticsService;
    
    @GetMapping("/system-settings")
    public String systemSettings(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("subjects", subjectService.findAllSubjects());
        model.addAttribute("classes", studentClassService.findAllClasses());
        model.addAttribute("departments", departmentService.findAllDepartments());
        model.addAttribute("majors", majorService.findAllMajors());
        
        return "admin/system-settings";
    }
    
    @GetMapping("/statistics")
    public String statistics(@RequestParam(required = false) String semester,
                            @RequestParam(required = false) Long departmentId,
                            @RequestParam(required = false) Long majorId,
                            @RequestParam(required = false) Long classId,
                            Model model) {
        
        Map<String, Object> stats = statisticsService.getStatistics(semester, departmentId, majorId, classId);
        
        model.addAttribute("statistics", stats);
        model.addAttribute("departments", departmentService.findAllDepartments());
        model.addAttribute("majors", majorService.findAllMajors());
        model.addAttribute("classes", studentClassService.findAllClasses());
        model.addAttribute("selectedSemester", semester);
        model.addAttribute("selectedDepartmentId", departmentId);
        model.addAttribute("selectedMajorId", majorId);
        model.addAttribute("selectedClassId", classId);
        
        return "admin/statistics";
    }
    
    // 用户管理
    @PostMapping("/user/create")
    public String createUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("success", "用户创建成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "用户创建失败：" + e.getMessage());
        }
        return "redirect:/admin/system-settings";
    }
    
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user, 
                           RedirectAttributes redirectAttributes) {
        try {
            user.setId(id);
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("success", "用户更新成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "用户更新失败：" + e.getMessage());
        }
        return "redirect:/admin/system-settings";
    }
    
    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("success", "用户删除成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "用户删除失败：" + e.getMessage());
        }
        return "redirect:/admin/system-settings";
    }
    
    // 科目管理
    @PostMapping("/subject/create")
    public String createSubject(@ModelAttribute Subject subject, RedirectAttributes redirectAttributes) {
        try {
            subjectService.createSubject(subject);
            redirectAttributes.addFlashAttribute("success", "科目创建成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "科目创建失败：" + e.getMessage());
        }
        return "redirect:/admin/system-settings";
    }
    
    // 班级管理
    @PostMapping("/class/create")
    public String createClass(@ModelAttribute StudentClass studentClass, RedirectAttributes redirectAttributes) {
        try {
            studentClassService.createClass(studentClass);
            redirectAttributes.addFlashAttribute("success", "班级创建成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "班级创建失败：" + e.getMessage());
        }
        return "redirect:/admin/system-settings";
    }
    
    // 院系管理
    @PostMapping("/department/create")
    public String createDepartment(@ModelAttribute Department department, RedirectAttributes redirectAttributes) {
        try {
            departmentService.createDepartment(department);
            redirectAttributes.addFlashAttribute("success", "院系创建成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "院系创建失败：" + e.getMessage());
        }
        return "redirect:/admin/system-settings";
    }
    
    // 专业管理
    @PostMapping("/major/create")
    public String createMajor(@ModelAttribute Major major, RedirectAttributes redirectAttributes) {
        try {
            majorService.createMajor(major);
            redirectAttributes.addFlashAttribute("success", "专业创建成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "专业创建失败：" + e.getMessage());
        }
        return "redirect:/admin/system-settings";
    }
}