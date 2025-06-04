package com.example.testDemo.services;

//import java.util.ArrayList;
//import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.testDemo.models.Student;
import com.example.testDemo.repository.studentRepo;

@Service
public class StudentService {

    @Autowired
    private studentRepo studentRepo;
    // private final List<Student> students = new ArrayList<>();

    public Student addStudent(Student student) {
        // students.add(student);
        studentRepo.save(student);
        return student;
    }

    public Iterable<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Optional<Student> updateStudent(Integer id, Student student) {
        return studentRepo.findById(id).map(exStudent -> {
            exStudent.setName(student.getName());
            studentRepo.save(exStudent);
            return exStudent;
        });
    }

    public Optional<Student> deleteStudent(Integer id) {
        studentRepo.deleteById(id);
        return null;
    }

    public Optional<Student> eachStudentInfo(Integer id) {
        return studentRepo.findById(id);
    }
}
