package com.student.service;

import com.student.entity.Student;
import com.student.entity.Subject;
import com.student.repository.StudentRepository;
import com.student.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;

    public Student createStudent(Student student, List<Long> subjectIds) {
        for (Long subjectId : subjectIds) {
            Optional<Subject> subject = subjectRepository.findById(subjectId);
            subject.ifPresent(student.getSubjects()::add);
        }
        return studentRepository.save(student);
    }


    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
