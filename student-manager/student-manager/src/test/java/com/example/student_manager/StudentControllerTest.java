package com.example.student_manager;

import com.example.student_manager.controllers.StudentController;
import com.example.student_manager.entities.Student;
import com.example.student_manager.services.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.fasterxml.jackson.databind.cfg.CoercionInputShape.Array;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StudentControllerTest {
    @InjectMocks
    private StudentController studentController;
    @Mock
    private StudentService studentService;

    @Test //Junit5
    void testSaveStudent() {
        //creer un student pour le test
        Student student = new Student();
        student.setId(1);
        student.setNom("Med");

        //MOCKITO :Simule le comportement de la méthode save du service pour retourner un objet Student lorsqu'elle est appelée avec n'importe quel étudiant.
        when(studentService.save(any(Student.class))).thenReturn(student) ;
        //Cela évite d'appeler réellement la méthode save qui pourrait nécessiter une base de données.
        //appeler le controlleur pour sauvegarder letudiant
        ResponseEntity<Student> response = studentController.save(student) ;

        //verifier le status de la reponse et l'etudiant sauvegardé
        assertEquals(HttpStatus.CREATED, response.getStatusCode()); //201
        assertEquals("Med", response.getBody().getNom());
    }
    @Test
    void testDeleteStudent() {
        when(studentService.delete(1)).thenReturn(true);
        ResponseEntity<Void> response = studentController.delete(1) ;
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); //204

    }

    @Test
    void testFindAllStudents() {
        Student student1 = new Student();
        Student student2 = new Student();

        when(studentService.findAll()).thenReturn(Arrays.asList(student1,student2)) ;

        ResponseEntity<List<Student>> response = studentController.findAll() ;

        assertEquals(2,response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode()); //200
    }

    @Test
    void testCountStudents() {
        when(studentService.countStudents()).thenReturn(10L); //
        ResponseEntity<Long> response = studentController.countStudent() ;

        assertEquals(10L, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void testFindByYear(){

        when(studentService.findNbrStudentByYear()).thenReturn(Arrays.asList());

        ResponseEntity<Collection<?>> response = studentController.findByYear() ;

        assertEquals(0,response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
