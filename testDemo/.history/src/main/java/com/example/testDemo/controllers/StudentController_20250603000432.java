package com.example.testDemo.controllers;

//import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testDemo.models.Student;
import com.example.testDemo.services.StudentService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("student")
    public Student addStudent(@RequestBody Student student) {
        System.out.println(student);
        return studentService.addStudent(student);
    }

    @GetMapping("student")
    public Iterable<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PutMapping("student/{id}")
    public Optional<Student> updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("student/{id}")
    public Optional<Student> deleteStudent(@PathVariable Integer id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("student/{id}")
    public Optional<Student> eachStudentInfo(@PathVariable Integer id) {
        return studentService.eachStudentInfo(id);
    }

}
