package com.example.student_manager.services;

import com.example.student_manager.entities.Student;
import com.example.student_manager.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.stereotype.Service ;


import java.util.Collection;
import java.util.List;
import java.util.Optional;


//indique que cette classe contient la logique metier
@Service
public class StudentService {


    //indique que spring doit inject une instance de sr là
    @Autowired
    private StudentRepository studentRepository;

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public boolean delete(int id ){

        //Optional<Student> encapsule l'objet Student retourné par findById
        Optional<Student> studentOptional = Optional.ofNullable(studentRepository.findById(id));
        if(studentOptional.isPresent()){
            studentRepository.delete(studentOptional.get());
            return true;
        }else{
            return false;
    }
}

    public List<Student> findAll(){
    return studentRepository.findAll() ;
    }

    public long countStudents(){
        return studentRepository.count() ;
    }

    public Collection<?> findNbrStudentByYear(){
        return studentRepository.findNbrStudentByYear();
    }

}