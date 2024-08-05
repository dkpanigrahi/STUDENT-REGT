package com.student.controller;

import com.student.entity.Student;
import com.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Student> createStudent(@RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        String address = (String) request.get("address");
        List<Integer> subjectIds = (List<Integer>) request.get("subjectIds");

        Student student = new Student();
        student.setName(name);
        student.setAddress(address);

        // Convert Integer to Long
        List<Long> longSubjectIds = subjectIds.stream().map(Long::valueOf).toList();

        Student createdStudent = studentService.createStudent(student, longSubjectIds);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    
    @GetMapping("/currentUser")
    public String currentUser(Principal p) {
    	return p.getName();
    }
}
