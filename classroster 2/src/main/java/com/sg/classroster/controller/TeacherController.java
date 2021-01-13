/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.classroster.controller;

import com.sg.classroster.dao.CourseDao;
import com.sg.classroster.dao.StudentDao;
import com.sg.classroster.dao.TeacherDao;
import com.sg.classroster.entities.Teacher;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author brockpace
 */
@Controller
public class TeacherController {
    Set<ConstraintViolation<Teacher>> violations = new HashSet<>();
    Set<ConstraintViolation<Teacher>> editViolations = new HashSet<>();
    
    @Autowired
    TeacherDao teacherDao;
    
    @Autowired
    StudentDao studentDao;
    
    @Autowired
    CourseDao courseDao;
    
    @GetMapping("teachers")
    public String displayTeachers(Model model) {
        editViolations.clear();
        List<Teacher> teachers = teacherDao.getAllTeachers();
        model.addAttribute("teachers", teachers);
        
        model.addAttribute("errors", violations);
        
        return "teachers";
    }
    
    @GetMapping("deleteTeacher")
    public String deleteTeacher(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        teacherDao.deleteTeacherById(id);
        
        return "redirect:/teachers";
    }
    
    @GetMapping("editTeacher")
    public String editTeacher(HttpServletRequest request, Model model) {
        violations.clear();
        int id = Integer.parseInt(request.getParameter("id"));
        Teacher teacher = teacherDao.getTeacherById(id);
        
        model.addAttribute("teacher", teacher);
        model.addAttribute("errors", editViolations);
        return "editTeacher";
    }
    
    @PostMapping("addTeacher")
    public String addTeacher(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String specialty = request.getParameter("specialty");
        
        Teacher teacher = new Teacher();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setSpecialty(specialty);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(teacher);
        
        if(violations.isEmpty()) {
            teacherDao.addTeacher(teacher);
        }
        
        return "redirect:/teachers";
    }
    
    @PostMapping("editTeacher")
    public String performEditTeacher(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Teacher teacher = teacherDao.getTeacherById(id);
        
        teacher.setFirstName(request.getParameter("firstName"));
        teacher.setLastName(request.getParameter("lastName"));
        teacher.setSpecialty(request.getParameter("specialty"));
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        editViolations = validate.validate(teacher);
        
        if(editViolations.isEmpty()) {        
            teacherDao.updateTeacher(teacher);            
            return "redirect:/teachers";
        }
        model.addAttribute("teacher", teacher);
        model.addAttribute("errors", editViolations);
        return ("editTeacher");
    }
}
