package com.example.testDemo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.testDemo.models.Student;

public interface studentRepo extends CrudRepository<Student, Integer> {

}

