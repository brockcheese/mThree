/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.classroster.service;

import com.bp.classroster.dto.Student;
import com.bp.classroster.dao.ClassRosterPersistenceException;
import java.util.List;

/**
 *
 * @author BrockPace
 */
public interface ClassRosterServiceLayer {
    
    void createStudent(Student student) throws
            ClassRosterDuplicateIdException,
            ClassRosterDataValidationException,
            ClassRosterPersistenceException;
    
    List<Student> getAllStudents() throws
            ClassRosterPersistenceException;
    
    Student getStudent(String studentId) throws
            ClassRosterPersistenceException;
    
    Student removeStudent(String studentId) throws
            ClassRosterPersistenceException;
    
}
