package com.grade.controller;

import com.grade.entity.Grade;
import com.grade.entity.Subject;
import com.grade.entity.User;
import com.grade.entity.StudentClass;
import com.grade.service.GradeService;
import com.grade.service.UserService;
import com.grade.service.SubjectService;
import com.grade.service.StudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    
    @Autowired
    private GradeService gradeService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private StudentClassService studentClassService;
    
    @GetMapping("/grades")
    public String viewGrades(@RequestParam(required = false) Long classId,
                           @RequestParam(required = false) Long subjectId,
                           @RequestParam(required = false) String semester,
                           Model model) {
        List<Grade> grades;
        
        if (classId != null && subjectId != null && semester != null) {
            grades = gradeService.findGradesByClassAndSubjectAndSemester(classId, subjectId, semester);
        } else if (classId != null) {
            grades = gradeService.findGradesByClass(classId);
        } else {
            grades = gradeService.findAllGrades();
        }
        
        model.addAttribute("grades", grades);
        model.addAttribute("classes", studentClassService.findAllClasses());
        model.addAttribute("subjects", subjectService.findAllSubjects());
        model.addAttribute("selectedClassId", classId);
        model.addAttribute("selectedSubjectId", subjectId);
        model.addAttribute("selectedSemester", semester);
        
        return "teacher/grades";
    }
    
    @GetMapping("/grade-input")
    public String gradeInputForm(@RequestParam(required = false) Long classId,
                               @RequestParam(required = false) Long subjectId,
                               Model model) {
        if (classId != null && subjectId != null) {
            List<User> students = userService.findStudentsByClass(classId);
            Optional<Subject> subject = subjectService.findById(subjectId);
            
            model.addAttribute("students", students);
            model.addAttribute("subject", subject.orElse(null));
            model.addAttribute("classId", classId);
            model.addAttribute("subjectId", subjectId);
        }
        
        model.addAttribute("classes", studentClassService.findAllClasses());
        model.addAttribute("subjects", subjectService.findAllSubjects());
        
        return "teacher/grade-input";
    }
    
    @PostMapping("/grade-input")
    public String saveGrades(@RequestParam Long classId,
                           @RequestParam Long subjectId,
                           @RequestParam String semester,
                           @RequestParam String academicYear,
                           @RequestParam List<Long> studentIds,
                           @RequestParam List<String> scores,
                           @RequestParam(required = false) List<String> examDates,
                           @RequestParam(required = false) List<String> remarks,
                           Authentication auth,
                           RedirectAttributes redirectAttributes) {
        try {
            Optional<User> teacher = userService.findByUsername(auth.getName());
            if (teacher.isPresent()) {
                gradeService.batchSaveGrades(studentIds, subjectId, scores, semester, 
                                           academicYear, examDates, remarks, teacher.get());
                redirectAttributes.addFlashAttribute("success", "成绩录入成功！");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "成绩录入失败：" + e.getMessage());
        }
        
        return "redirect:/teacher/grades";
    }
    
    @PostMapping("/import-excel")
    public String importExcel(@RequestParam("file") MultipartFile file,
                            Authentication auth,
                            RedirectAttributes redirectAttributes) {
        try {
            Optional<User> teacher = userService.findByUsername(auth.getName());
            if (teacher.isPresent()) {
                List<Grade> importedGrades = gradeService.importGradesFromExcel(file, teacher.get());
                redirectAttributes.addFlashAttribute("success", 
                    "成功导入 " + importedGrades.size() + " 条成绩记录！");
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "文件读取失败：" + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "导入失败：" + e.getMessage());
        }
        
        return "redirect:/teacher/grades";
    }
    
    @GetMapping("/grade/edit/{id}")
    public String editGradeForm(@PathVariable Long id, Model model) {
        Optional<Grade> grade = gradeService.findById(id);
        if (grade.isPresent()) {
            model.addAttribute("grade", grade.get());
            model.addAttribute("subjects", subjectService.findAllSubjects());
            return "teacher/grade-edit";
        }
        return "redirect:/teacher/grades";
    }
    
    @PostMapping("/grade/edit/{id}")
    public String updateGrade(@PathVariable Long id,
                            @ModelAttribute Grade grade,
                            RedirectAttributes redirectAttributes) {
        try {
            grade.setId(id);
            gradeService.updateGrade(grade);
            redirectAttributes.addFlashAttribute("success", "成绩更新成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "成绩更新失败：" + e.getMessage());
        }
        
        return "redirect:/teacher/grades";
    }
    
    @PostMapping("/grade/delete/{id}")
    public String deleteGrade(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            gradeService.deleteGrade(id);
            redirectAttributes.addFlashAttribute("success", "成绩删除成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "成绩删除失败：" + e.getMessage());
        }
        
        return "redirect:/teacher/grades";
    }
}