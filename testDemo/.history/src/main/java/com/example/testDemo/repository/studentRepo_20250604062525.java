package com.example.testDemo.repository;

import org.springframework.data.repository.JpaRepository;

import com.example.testDemo.models.Student;

public interface studentRepo extends JpaRepository<Student, Integer> {

}

