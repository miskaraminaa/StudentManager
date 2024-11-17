package com.example.student_manager.controllers;

import com.example.student_manager.entities.Student;
import com.example.student_manager.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

//controlleur REST gère les req HTTP
@RestController
@RequestMapping("/students")

public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<Student> save(@RequestBody Student student) {
        Student savedStudent = studentService.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id){
        boolean deleted = studentService.delete(id);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> findAll(){
        List<Student> students = studentService.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countStudent(){
        long count = studentService.countStudents();
        return new ResponseEntity<>(count, HttpStatus.OK) ;
    }

    @GetMapping("/byYear")
    public ResponseEntity<Collection<?>> findByYear() {
        Collection<?> studentsByYear = studentService.findNbrStudentByYear() ;
        return new ResponseEntity<>(studentsByYear, HttpStatus.OK);
    }
    @Operation(summary = "Recuperer tous les étudiants")
    @GetMapping("/students")
    public List<Student> getAllStudents(){
        return studentService.findAll();
    }
}
